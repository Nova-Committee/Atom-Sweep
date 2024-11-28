package committee.nova.mods.atom.sweep.common.core;


import committee.nova.mods.atom.sweep.common.Constants;
import committee.nova.mods.atom.sweep.common.SweepCommon;
import committee.nova.mods.atom.sweep.common.config.Config;
import committee.nova.mods.atom.sweep.common.core.model.ASItem;
import committee.nova.mods.atom.sweep.common.core.model.ASMob;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.entity.vehicle.Boat;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Project: clean
 * Author: cnlimiter
 * Date: 2022/12/8 18:46
 * Description:
 */
public class Sweeper {
    public static final Sweeper INSTANCE = new Sweeper();

    private Timer timer;
    private TimerTask currentTask;

    private Sweeper() {
    }

    public void startSweep() {
        if (this.timer != null) {
            this.stopSweep();
        }

        this.timer = new Timer();
        this.resetTimer();
    }

    public void stopSweep() {
        this.timer.cancel();
        this.timer = null;
    }

    public void resetTimer() {
        if (this.currentTask != null) {
            this.currentTask.cancel();
        }

        this.timer.purge();
        this.currentTask = new TimerTask() {
            public void run() {
                if (Constants.SERVER != null) {
                    Constants.SERVER.execute(Sweeper.this::noticeSweep);
                }

                Sweeper.this.timer.schedule(new TimerTask() {
                    public void run() {
                        if (Constants.SERVER != null) {
                            Constants.SERVER.execute(Sweeper.this::startSweepTick);
                        }

                    }
                }, (long) (Config.COMMON.sweepNotify.get() - Config.COMMON.sweepDiscount.get()) * 1000L);
            }
        };
        this.timer.schedule(this.currentTask, 0L, (long) Config.COMMON.sweepPeriod.get() * 60L * 1000L);
    }

    public void startSweepTick() {
        SweepCommon.counter = Config.COMMON.sweepDiscount.get() * 20;
    }

    public void noticeSweep() {
        Constants.sendMessageToAllPlayers(Config.COMMON.sweepNotice.get(), Config.COMMON.sweepNotify.get());
    }


    public void sweep(MinecraftServer server) {
        int killItemCount = 0;
        int killLivingCount = 0;
        int killXpCount = 0;
        int killOtherCount = 0;
        Iterable<ServerLevel> worlds = server.getAllLevels();

        for (ServerLevel world : worlds) {
            if (Config.COMMON.isItemEntityCleanupEnable.get()) {
                    killItemCount += cleanupItemEntity(world);
                }
            if (Config.COMMON.isMobEntityCleanupEnable.get()) {
                if (Config.COMMON.isAnimalEntitiesCleanupEnable.get())
                        killLivingCount += cleanupAnimalEntity(world);
                if (Config.COMMON.isMonsterEntitiesCleanupEnable.get())
                        killLivingCount += cleanupMonsterEntity(world);
                }
            if (Config.COMMON.isExperienceOrbEntityCleanupEnable.get())
                    killXpCount += cleanupXpEntity(world);

                killOtherCount += cleanOtherEntities(world);
        }

        Constants.sendMessageToAllPlayers(server, Config.COMMON.sweepNoticeComplete.get(), killItemCount, killLivingCount, killXpCount, killOtherCount);
    }

    public int cleanupItemEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> entity instanceof ItemEntity, entity -> new ASItem((ItemEntity) entity).filtrate());
    }

    public int cleanupMonsterEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> entity instanceof Monster,
                entity -> new ASMob((Mob) entity).filtrate());
    }

    public int cleanupAnimalEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> (entity instanceof Mob) && !(entity instanceof Monster),
                entity -> new ASMob((Mob) entity).filtrate());
    }

    public int cleanupXpEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> entity instanceof ExperienceOrb, entity -> true);
    }

    private int cleanupEntity(ServerLevel world, Predicate<Entity> type, Predicate<Entity> additionalPredicate) {
        AtomicInteger amount = new AtomicInteger();
        List<Entity> entities = Collections.synchronizedList(new ArrayList<>());//读多写少的线程安全
        for (var e : world.getAllEntities()) {
            entities.add(e);
        }

        try {
            synchronized (entities) {
                entities.stream()
                        .filter(Objects::nonNull)
                        .filter(entity -> entity.getCustomName() == null)
                        .filter(type)
                        .filter(additionalPredicate)
                        .forEach(
                                entity -> {
                                    if (Config.COMMON.isExpOn.get()) {
                                        entity.kill();
                                    } else {
                                        entity.discard();
                                    }
                                    if (entity instanceof ItemEntity) {
                                        amount.getAndAdd(((ItemEntity) entity).getItem().getCount());
                                    } else {
                                        amount.getAndIncrement();
                                    }
                                }
                        );
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }

        return amount.get();
    }


    public int cleanOtherEntities(ServerLevel world) {
        int amount = 0;

        if (Config.COMMON.isFallingBlocksEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof FallingBlockEntity, entity -> true);
        if (Config.COMMON.isArrowEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof AbstractArrow, entity -> !(entity instanceof ThrownTrident));
        if (Config.COMMON.isTridentEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof ThrownTrident, entity -> true);
        if (Config.COMMON.isDamagingProjectileEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof AbstractHurtingProjectile, entity -> true);
        if (Config.COMMON.isShulkerBulletEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof ShulkerBullet, entity -> true);
        if (Config.COMMON.isFireworkRocketEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof FireworkRocketEntity, entity -> true);
        if (Config.COMMON.isItemFrameEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof ItemFrame, entity -> true);
        if (Config.COMMON.isPaintingEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof Painting, entity -> true);
        if (Config.COMMON.isBoatEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof Boat, entity -> true);
        if (Config.COMMON.isTNTEntityCleanupEnable.get())
            amount += cleanupEntity(world, entity -> entity instanceof PrimedTnt, entity -> true);
        return amount;
    }

}

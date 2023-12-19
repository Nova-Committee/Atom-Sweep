package nova.committee.atom.sweep.core;


import lombok.val;
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
import net.minecraft.world.item.Item;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.model.ASItem;
import nova.committee.atom.sweep.core.model.ASMob;
import nova.committee.atom.sweep.init.config.ModConfig;
import nova.committee.atom.sweep.init.handler.SweepHandler;

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
        this.stopSweep();

        this.timer = new Timer();
        this.resetTimer();
    }

    public void stopSweep() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public void resetTimer() {
        if (this.currentTask != null) {
            this.currentTask.cancel();
        }

        this.timer.purge();
        this.currentTask = new TimerTask() {
            public void run() {
                if (Static.SERVER != null) {
                    Static.SERVER.execute(Sweeper.this::noticeSweep);
                }

                Sweeper.this.timer.schedule(new TimerTask() {
                    public void run() {
                        if (Static.SERVER != null) {
                            Static.SERVER.execute(Sweeper.this::startSweepTick);
                        }

                    }
                }, (long) (ModConfig.INSTANCE.getCommon().getSweepNotify() - ModConfig.INSTANCE.getCommon().getSweepDiscount()) * 1000L);
            }
        };
        this.timer.schedule(this.currentTask, 0L, (long) ModConfig.INSTANCE.getCommon().getSweepPeriod() * 60L * 1000L);
    }

    public void startSweepTick() {
        SweepHandler.beginSweepCountDown();
    }

    public void noticeSweep() {
        Static.sendMessageToAllPlayers(ModConfig.INSTANCE.getCommon().getSweepNotice(), ModConfig.INSTANCE.getCommon().getSweepNotify());
    }


    public void sweep(MinecraftServer server) {
        int killItemCount = 0;
        int killLivingCount = 0;
        int killXpCount = 0;
        int killOtherCount = 0;

        Iterable<ServerLevel> worlds = server.getAllLevels();

        for (ServerLevel world : worlds) {
            if (ModConfig.INSTANCE.getItem().isItemEntityCleanupEnable()) {
                killItemCount += cleanupItemEntity(world);
            }
            if (ModConfig.INSTANCE.getMob().isMobEntityCleanupEnable()) {
                if (ModConfig.INSTANCE.getMob().isAnimalEntitiesCleanupEnable())
                        killLivingCount += cleanupAnimalEntity(world);
                if (ModConfig.INSTANCE.getMob().isMonsterEntitiesCleanupEnable())
                        killLivingCount += cleanupMonsterEntity(world);
            }
            if (ModConfig.INSTANCE.getOther().isExperienceOrbEntityCleanupEnable())
                killXpCount += cleanupXpEntity(world);

            killOtherCount += cleanOtherEntities(world);


        }

        Static.sendMessageToAllPlayers(server, ModConfig.INSTANCE.getCommon().getSweepNoticeComplete(), killItemCount, killLivingCount, killXpCount, killOtherCount);
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
        for (val e : world.getAllEntities()) {
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
                                    entity.kill();
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

        if (ModConfig.INSTANCE.getOther().isFallingBlocksEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof FallingBlockEntity, entity -> true);
        if (ModConfig.INSTANCE.getOther().isArrowEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof AbstractArrow, entity -> !(entity instanceof ThrownTrident));
        if (ModConfig.INSTANCE.getOther().isTridentEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof ThrownTrident, entity -> true);
        if (ModConfig.INSTANCE.getOther().isDamagingProjectileEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof AbstractHurtingProjectile, entity -> true);
        if (ModConfig.INSTANCE.getOther().isShulkerBulletEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof ShulkerBullet, entity -> true);
        if (ModConfig.INSTANCE.getOther().isFireworkRocketEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof FireworkRocketEntity, entity -> true);
        if (ModConfig.INSTANCE.getOther().isItemFrameEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof ItemFrame, entity -> true);
        if (ModConfig.INSTANCE.getOther().isPaintingEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof Painting, entity -> true);
        if (ModConfig.INSTANCE.getOther().isBoatEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof Boat, entity -> true);
        if (ModConfig.INSTANCE.getOther().isTNTEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof PrimedTnt, entity -> true);
        return amount;
    }

}

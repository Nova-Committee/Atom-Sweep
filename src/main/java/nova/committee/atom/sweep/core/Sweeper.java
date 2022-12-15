package nova.committee.atom.sweep.core;


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
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.model.AESItemEntity;
import nova.committee.atom.sweep.core.model.AESMobEntity;
import nova.committee.atom.sweep.init.handler.SweepHandler;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

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
                if (Static.SERVER != null) {
                    Static.SERVER.execute(Sweeper.this::noticeSweep);
                }

                Sweeper.this.timer.schedule(new TimerTask() {
                    public void run() {
                        if (Static.SERVER != null) {
                            Static.SERVER.execute(Sweeper.this::startSweepTick);
                        }

                    }
                }, (long) (Static.config.getCommon().getSweepNotify() - Static.config.getCommon().getSweepDiscount()) * 1000L);
            }
        };
        this.timer.schedule(this.currentTask, 0L, (long) Static.config.getCommon().getSweepPeriod() * 60L * 1000L);
    }

    public void startSweepTick() {
        SweepHandler.beginSweepCountDown();
    }

    public void noticeSweep() {
        Static.sendMessageToAllPlayers(Static.config.getCommon().getSweepNotice(), Static.config.getCommon().getSweepNotify());
    }


    public void sweep(MinecraftServer server) {
        int killItemCount = 0;
        int killLivingCount = 0;
        int killXpCount = 0;
        int killOtherCount = 0;
        Iterable<ServerLevel> worlds = server.getAllLevels();

        for (ServerLevel world : worlds) {
            synchronized (world) {
                if (Static.config.getItemsClean().isItemEntityCleanupEnable()) {
                    killItemCount += cleanupItemEntity(world);
                }
                if (Static.config.getMobsClean().isMobEntityCleanupEnable()) {
                    if (Static.config.getMobsClean().isAnimalEntitiesCleanupEnable())
                        killLivingCount += cleanupAnimalEntity(world);
                    if (Static.config.getMobsClean().isMonsterEntitiesCleanupEnable())
                        killLivingCount += cleanupMonsterEntity(world);
                }
                if (Static.config.getOthersClean().isExperienceOrbEntityCleanupEnable())
                    killXpCount += cleanupXpEntity(world);

                killOtherCount += cleanOtherEntities(world);

            }
        }

        Static.sendMessageToAllPlayers(server, Static.config.getCommon().getSweepNoticeComplete(), killItemCount, killLivingCount, killXpCount, killOtherCount);
    }

    public int cleanupItemEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> entity instanceof ItemEntity, entity -> new AESItemEntity((ItemEntity) entity).filtrate());
    }

    public int cleanupMonsterEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> entity instanceof Monster,
                entity -> new AESMobEntity((Mob) entity).filtrate());
    }

    public int cleanupAnimalEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> (entity instanceof Mob) && !(entity instanceof Monster),
                entity -> new AESMobEntity((Mob) entity).filtrate());
    }

    public int cleanupXpEntity(ServerLevel world) {
        return cleanupEntity(world, entity -> entity instanceof ExperienceOrb, entity -> true);
    }

    private int cleanupEntity(ServerLevel world, Predicate<Entity> type, Predicate<Entity> additionalPredicate) {
        AtomicInteger amount = new AtomicInteger();

        StreamSupport.stream(world.getAllEntities().spliterator(), false)
                .filter(Objects::nonNull)
                .filter(entity -> entity.getCustomName() != null)
                .filter(type)
                .filter(additionalPredicate)
                .forEach(
                        entity -> {
                            entity.remove(Entity.RemovalReason.KILLED);
                            if (entity instanceof ItemEntity) {
                                amount.getAndAdd(((ItemEntity) entity).getItem().getCount());
                            } else {
                                amount.getAndIncrement();
                            }
                        }
                );

        return amount.get();
    }


    public int cleanOtherEntities(ServerLevel world) {
        int amount = 0;

        if (Static.config.getOthersClean().isFallingBlocksEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof FallingBlockEntity, entity -> true);
        if (Static.config.getOthersClean().isArrowEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof AbstractArrow, entity -> !(entity instanceof ThrownTrident));
        if (Static.config.getOthersClean().isTridentEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof ThrownTrident, entity -> true);
        if (Static.config.getOthersClean().isDamagingProjectileEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof AbstractHurtingProjectile, entity -> true);
        if (Static.config.getOthersClean().isShulkerBulletEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof ShulkerBullet, entity -> true);
        if (Static.config.getOthersClean().isFireworkRocketEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof FireworkRocketEntity, entity -> true);
        if (Static.config.getOthersClean().isItemFrameEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof ItemFrame, entity -> true);
        if (Static.config.getOthersClean().isPaintingEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof Painting, entity -> true);
        if (Static.config.getOthersClean().isBoatEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof Boat, entity -> true);
        if (Static.config.getOthersClean().isTNTEntityCleanupEnable())
            amount += cleanupEntity(world, entity -> entity instanceof PrimedTnt, entity -> true);
        return amount;
    }

}

package nova.committee.atom.clean.init.handler;


import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.clean.Static;
import nova.committee.atom.clean.core.model.AESItemEntity;
import nova.committee.atom.clean.core.model.AESMobEntity;
import nova.committee.atom.clean.util.I18Util;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:00
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CleanerHandler {




    private static long clearItemTimer = 0;
    private static boolean isCleanupItemCountdownMessageSent = false;
    private static boolean isCleanupMobMessageSent = false;
    private static long clearMobTimer = 0;
    private static long otherTimer = 0;
    private static int counter = 0;


    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (counter >= 2 * 20) {
            if (event.phase == TickEvent.Phase.END) {
                counter = 0;
                Optional.ofNullable(Static.SERVER).ifPresent(server -> {
                    Iterable<ServerWorld> worlds = server.getAllLevels();

                    // item entities cleaner
                    if (Static.config.getItemsClean().isItemEntityCleanupEnable()) {
                        long nextCleanupTime = clearItemTimer + Static.config.getItemsClean().getCleanupItemEntitiesIntervalSeconds() * 1000L;
                        // countdown
                        if (nextCleanupTime - System.currentTimeMillis() <= Static.config.getItemsClean().getCleanupItemEntitiesCountdownSeconds() * 1000L && !isCleanupItemCountdownMessageSent) {
                            sendMessage(Static.config.getItemsClean().getCleanupItemEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
                                    I18Util.getTranslationKey("message", "cleanupItemCountdown"), Static.config.getItemsClean().getCleanupItemEntitiesCountdownSeconds()), Static.config.getItemsClean().getCleanupItemEntitiesCountdownSeconds());
                            isCleanupItemCountdownMessageSent = true;
                        }
                        // real clean
                        if (nextCleanupTime <= System.currentTimeMillis()) {
                            int amount = cleanupEntity(worlds, entity -> entity instanceof ItemEntity, entity -> new AESItemEntity((ItemEntity) entity).filtrate());
                            clearItemTimer = System.currentTimeMillis();
                            isCleanupItemCountdownMessageSent = false;
                            sendMessage(Static.config.getItemsClean().getCleanedupItemEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
                                    I18Util.getTranslationKey("message", "itemCleanupComplete"), amount), amount);
                        }
                    }

                    // mob entities cleaner
                    if (Static.config.getMobsClean().isMobEntityCleanupEnable()) {
                        long nextCleanupTime = clearMobTimer + Static.config.getMobsClean().getCleanupMobEntitiesIntervalSeconds() * 1000L;
                        if (nextCleanupTime - System.currentTimeMillis() <= Static.config.getMobsClean().getCleanupMobEntitiesCountdownSeconds() * 1000L && !isCleanupMobMessageSent) {
                            sendMessage(Static.config.getMobsClean().getCleanupMobEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
                                    I18Util.getTranslationKey("message", "cleanupMobCountdown"), Static.config.getMobsClean().getCleanupMobEntitiesCountdownSeconds()), Static.config.getMobsClean().getCleanupMobEntitiesCountdownSeconds());
                            isCleanupMobMessageSent = true;
                        }
                        if (nextCleanupTime <= System.currentTimeMillis()) {
                            int amount = 0;
                            if (Static.config.getMobsClean().isAnimalEntitiesCleanupEnable())
                                amount += cleanupEntity(worlds, entity -> (entity instanceof MobEntity) && !(entity instanceof MonsterEntity),
                                        entity -> new AESMobEntity((MobEntity) entity).filtrate());
                            if (Static.config.getMobsClean().isMonsterEntitiesCleanupEnable())
                                amount += cleanupEntity(worlds, entity -> entity instanceof MonsterEntity, entity -> new AESMobEntity((MobEntity) entity).filtrate());
                            clearMobTimer = System.currentTimeMillis();
                            isCleanupMobMessageSent = false;
                            sendMessage(Static.config.getMobsClean().getCleanedupMobEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
                                    I18Util.getTranslationKey("message", "mobCleanupComplete"), amount), amount);
                        }
                    }

                    // Other entities cleaner
                    if (otherTimer + Static.config.getOthersClean().getCleanupOtherEntitiesIntervalSeconds() * 1000L <= System.currentTimeMillis()) {
                        int amount = cleanOtherEntities(worlds);
                        otherTimer = System.currentTimeMillis();
                    }

                });
            }
        }
        counter++;
    }

    public static int cleanOtherEntities(Iterable<ServerWorld> worlds) {
        int amount = 0;
        if (Static.config.getOthersClean().isExperienceOrbEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof ExperienceOrbEntity, entity -> true);
        if (Static.config.getOthersClean().isFallingBlocksEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof FallingBlockEntity, entity -> true);
        if (Static.config.getOthersClean().isArrowEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof AbstractArrowEntity, entity -> !(entity instanceof TridentEntity));
        if (Static.config.getOthersClean().isTridentEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof TridentEntity, entity -> true);
        if (Static.config.getOthersClean().isDamagingProjectileEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof DamagingProjectileEntity, entity -> true);
        if (Static.config.getOthersClean().isShulkerBulletEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof ShulkerBulletEntity, entity -> true);
        if (Static.config.getOthersClean().isFireworkRocketEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof FireworkRocketEntity, entity -> true);
        if (Static.config.getOthersClean().isItemFrameEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof ItemFrameEntity, entity -> true);
        if (Static.config.getOthersClean().isPaintingEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof PaintingEntity, entity -> true);
        if (Static.config.getOthersClean().isBoatEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof BoatEntity, entity -> true);
        if (Static.config.getOthersClean().isTNTEntityCleanupEnable())
            amount += cleanupEntity(worlds, entity -> entity instanceof TNTEntity, entity -> true);
        return amount;
    }

    public static int cleanupEntity(Iterable<ServerWorld> worlds, Predicate<Entity> type, Predicate<Entity> additionalPredicate) {
        AtomicInteger amount = new AtomicInteger();

        worlds.forEach(world ->
                StreamSupport.stream(world.getAllEntities().spliterator(), false)
                        .filter(Objects::nonNull)
                        .filter(entity -> entity.getCustomName() == null)
                        .filter(type)
                        .filter(additionalPredicate)
                        .forEach(
                                entity -> {
                                    entity.remove();
                                    if (entity instanceof ItemEntity ) {
                                        amount.getAndAdd(((ItemEntity)entity).getItem().getCount());
                                    } else {
                                        amount.getAndIncrement();
                                    }
                                }
                        )
                );
        return amount.get();
    }

    private static void sendMessage(String customizedMessage, ITextComponent defaultMessage, Object... formatters) {
        if ("null".equals(customizedMessage)) {
            Static.sendMessageToAllPlayers(defaultMessage, false);
        } else if (!customizedMessage.isEmpty()) {
            Static.sendMessageToAllPlayers(new StringTextComponent(String.format(customizedMessage, formatters)), false);
        }
    }
}

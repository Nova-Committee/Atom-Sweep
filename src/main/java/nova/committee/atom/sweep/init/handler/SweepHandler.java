package nova.committee.atom.sweep.init.handler;


import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;

import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:00
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SweepHandler {


    private static int counter = -1;

    public static void beginSweepCountDown() {
        counter = Static.config.getCommon().getSweepDiscount() * 20;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Optional.ofNullable(Static.SERVER).ifPresent(server -> {
                if (counter >= 0) {
                    if (counter == 0) {
                        Sweeper.INSTANCE.sweep(server);
                        counter = -1;
                    } else {
                        if (counter % 20 == 0) {
                            Static.sendMessageToAllPlayers(Static.config.getCommon().getSweepNotice(), counter / 20);
                        }

                        --counter;
                    }
                }
//                    // item entities cleaner
//                    if (Static.config.getItemsClean().isItemEntityCleanupEnable()) {
//                        long nextCleanupTime = clearItemTimer + Static.config.getItemsClean().getCleanupItemEntitiesIntervalSeconds() * 1000L;
//                        // countdown
//                        if (nextCleanupTime - System.currentTimeMillis() <= Static.config.getItemsClean().getCleanupItemEntitiesCountdownSeconds() * 1000L && !isCleanupItemCountdownMessageSent) {
//                            sendMessage(Static.config.getItemsClean().getCleanupItemEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
//                                    I18Util.getTranslationKey("message", "cleanupItemCountdown"), Static.config.getItemsClean().getCleanupItemEntitiesCountdownSeconds()), Static.config.getItemsClean().getCleanupItemEntitiesCountdownSeconds());
//                            isCleanupItemCountdownMessageSent = true;
//                        }
//                        // real clean
//                        if (nextCleanupTime <= System.currentTimeMillis()) {
//                            int amount = cleanupEntity(worlds, entity -> entity instanceof ItemEntity, entity -> new AESItemEntity((ItemEntity) entity).filtrate());
//                            clearItemTimer = System.currentTimeMillis();
//                            isCleanupItemCountdownMessageSent = false;
//                            sendMessage(Static.config.getItemsClean().getCleanedupItemEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
//                                    I18Util.getTranslationKey("message", "itemCleanupComplete"), amount), amount);
//                        }
//                    }
//
//                    // mob entities cleaner
//                    if (Static.config.getMobsClean().isMobEntityCleanupEnable()) {
//                        long nextCleanupTime = clearMobTimer + Static.config.getMobsClean().getCleanupMobEntitiesIntervalSeconds() * 1000L;
//                        if (nextCleanupTime - System.currentTimeMillis() <= Static.config.getMobsClean().getCleanupMobEntitiesCountdownSeconds() * 1000L && !isCleanupMobMessageSent) {
//                            sendMessage(Static.config.getMobsClean().getCleanupMobEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
//                                    I18Util.getTranslationKey("message", "cleanupMobCountdown"), Static.config.getMobsClean().getCleanupMobEntitiesCountdownSeconds()), Static.config.getMobsClean().getCleanupMobEntitiesCountdownSeconds());
//                            isCleanupMobMessageSent = true;
//                        }
//                        if (nextCleanupTime <= System.currentTimeMillis()) {
//                            int amount = 0;
//                            if (Static.config.getMobsClean().isAnimalEntitiesCleanupEnable())
//                                amount += cleanupEntity(worlds, entity -> (entity instanceof MobEntity) && !(entity instanceof MonsterEntity),
//                                        entity -> new AESMobEntity((MobEntity) entity).filtrate());
//                            if (Static.config.getMobsClean().isMonsterEntitiesCleanupEnable())
//                                amount += cleanupEntity(worlds, entity -> entity instanceof MonsterEntity, entity -> new AESMobEntity((MobEntity) entity).filtrate());
//                            clearMobTimer = System.currentTimeMillis();
//                            isCleanupMobMessageSent = false;
//                            sendMessage(Static.config.getMobsClean().getCleanedupMobEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
//                                    I18Util.getTranslationKey("message", "mobCleanupComplete"), amount), amount);
//                        }
//                    }
//
//                    // Other entities cleaner
//                    if (otherTimer + Static.config.getOthersClean().getCleanupOtherEntitiesIntervalSeconds() * 1000L <= System.currentTimeMillis()) {
//                        int amount = cleanOtherEntities(worlds);
//                        otherTimer = System.currentTimeMillis();
//                    }
            });
        }
    }

}

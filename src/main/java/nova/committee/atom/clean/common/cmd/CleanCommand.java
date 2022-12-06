package nova.committee.atom.clean.common.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import nova.committee.atom.clean.Static;
import nova.committee.atom.clean.core.model.AESItemEntity;
import nova.committee.atom.clean.core.model.AESMobEntity;
import nova.committee.atom.clean.init.config.CleanerConfig;
import nova.committee.atom.clean.init.handler.CleanerHandler;
import nova.committee.atom.clean.init.handler.ConfigHandler;
import nova.committee.atom.clean.util.I18Util;

import java.util.Optional;

import static nova.committee.atom.clean.init.handler.CleanerHandler.*;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 18:08
 * Version: 1.0
 */
public class CleanCommand {
    private static long clearItemTimer = 0;
    private static boolean isCleanupItemCountdownMessageSent = false;
    private static boolean isCleanupMobMessageSent = false;
    private static boolean isCleanupAnimalMessageSent = false;

    private static long clearMobTimer = 0;
    private static long otherTimer = 0;

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal(Static.MOD_ID)

                        .then(
                                Commands.literal("items")
                                        .executes(CleanCommand::itemsExe)
                        )
                        .then(
                                Commands.literal("monsters")
                                        .executes(CleanCommand::monstersExe)
                        )
                        .then(
                                Commands.literal("animals")
                                        .executes(CleanCommand::animalsExe)
                        )
                        .then(
                                Commands.literal("others")
                                        .executes(CleanCommand::othersExe)
                        )
                        .then(
                                   Commands.literal("white")
                                           .then(
                                                   Commands.literal("add")
                                                           .executes(CleanCommand::whiteAdd)

                                           )
                                           .then(
                                                   Commands.literal("del")
                                                           .executes(CleanCommand::whiteDel)
                                           )
                        )

                        .requires(context -> context.hasPermission(2))

        );
    }
    private static int whiteAdd(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null){
            Static.config.getItemsClean().addItemEntitiesWhitelist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经添加到白名单"), Util.NIL_UUID);
        }
        else {
            player.sendMessage(new StringTextComponent("添加到白名单失败"), Util.NIL_UUID);

        }
        return 1;
    }
    private static int whiteDel(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem().getRegistryName() != null){
            Static.config.getItemsClean().delItemEntitiesWhitelist(itemStack.getItem().getRegistryName().toString());
            ConfigHandler.onChange();
            player.sendMessage(new StringTextComponent("已经从白名单移除"), Util.NIL_UUID);
        }
        else {
            player.sendMessage(new StringTextComponent("从白名单移除失败"), Util.NIL_UUID);
        }
        return 1;
    }
    private static int itemsExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        long nextCleanupTime = clearItemTimer + 1 * 1000L;
        if (nextCleanupTime - System.currentTimeMillis() <= 1 * 1000L && !isCleanupItemCountdownMessageSent) {
            sendMessage(Static.config.getItemsClean().getCleanupItemEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
                    I18Util.getTranslationKey("message", "cleanupItemCountdown"), 1), 1);
            isCleanupItemCountdownMessageSent = true;
        }
        // real clean
        if (nextCleanupTime <= System.currentTimeMillis()) {
            int amount = cleanupEntity(context.getSource().getServer().getAllLevels(), entity -> entity instanceof ItemEntity, entity -> new AESItemEntity((ItemEntity) entity).filtrate());
            clearItemTimer = System.currentTimeMillis();
            isCleanupItemCountdownMessageSent = false;
            sendMessage(Static.config.getItemsClean().getCleanedupItemEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
                    I18Util.getTranslationKey("message", "itemCleanupComplete"), amount), amount);
        }
        return 1;
    }
    private static int monstersExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        long nextCleanupTime = clearMobTimer + 1 * 1000L;
        if (nextCleanupTime - System.currentTimeMillis() <= 1 * 1000L && !isCleanupMobMessageSent) {
            sendMessage(Static.config.getMobsClean().getCleanupMobEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
                    I18Util.getTranslationKey("message", "cleanupMonsterCountdown"), 1), 1);
            isCleanupMobMessageSent = true;
        }
        if (nextCleanupTime <= System.currentTimeMillis()) {
            int amount = 0;
            if (Static.config.getMobsClean().isAnimalEntitiesCleanupEnable())
                amount += cleanupEntity(context.getSource().getServer().getAllLevels(), entity -> (entity instanceof MobEntity) && !(entity instanceof MonsterEntity),
                        entity -> new AESMobEntity((MobEntity) entity).filtrate());
            clearMobTimer = System.currentTimeMillis();
            isCleanupMobMessageSent = false;
            sendMessage(Static.config.getMobsClean().getCleanedupMobEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
                    I18Util.getTranslationKey("message", "monsterCleanupComplete"), amount), amount);
        }
        return 1;
    }
    private static int animalsExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        long nextCleanupTime = clearMobTimer + 1 * 1000L;
        if (nextCleanupTime - System.currentTimeMillis() <= 1 * 1000L && !isCleanupAnimalMessageSent) {
            sendMessage(Static.config.getMobsClean().getCleanupMobEntitiesCountdownMessage(), I18Util.getYellowTextFromI18n(true, false,
                    I18Util.getTranslationKey("message", "cleanupAnimalCountdown"), 1), 1);
            isCleanupAnimalMessageSent = true;
        }
        if (nextCleanupTime <= System.currentTimeMillis()) {
            int amount = 0;
            if (Static.config.getMobsClean().isMonsterEntitiesCleanupEnable())
                amount += cleanupEntity(context.getSource().getServer().getAllLevels(), entity -> entity instanceof MonsterEntity, entity -> new AESMobEntity((MobEntity) entity).filtrate());
            clearMobTimer = System.currentTimeMillis();
            isCleanupAnimalMessageSent = false;
            sendMessage(Static.config.getMobsClean().getCleanedupMobEntitiesMessage(), I18Util.getGreenTextFromI18n(false, false,
                    I18Util.getTranslationKey("message", "animalCleanupComplete"), amount), amount);
        }
        return 1;
    }
    private static int othersExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        long amount = CleanerHandler.cleanOtherEntities(Static.SERVER.getAllLevels());
        return 1;
    }

    private static void sendMessage(String customizedMessage, ITextComponent defaultMessage, Object... formatters) {
        if ("null".equals(customizedMessage)) {
            Static.sendMessageToAllPlayers(defaultMessage, false);
        } else if (!customizedMessage.isEmpty()) {
            Static.sendMessageToAllPlayers(new StringTextComponent(String.format(customizedMessage, formatters)), false);
        }
    }
}

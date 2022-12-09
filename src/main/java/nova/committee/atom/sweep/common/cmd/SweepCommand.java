package nova.committee.atom.sweep.common.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;
import nova.committee.atom.sweep.init.handler.ConfigHandler;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 18:08
 * Version: 1.0
 */
public class SweepCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal(Static.MOD_ID)

                        .then(
                                Commands.literal("items")
                                        .executes(SweepCommand::itemsExe)
                        )
                        .then(
                                Commands.literal("monsters")
                                        .executes(SweepCommand::monstersExe)
                        )
                        .then(
                                Commands.literal("animals")
                                        .executes(SweepCommand::animalsExe)
                        )
                        .then(
                                Commands.literal("others")
                                        .executes(SweepCommand::othersExe)
                        )
                        .then(
                                Commands.literal("xps")
                                        .executes(SweepCommand::xpsExe)
                        )
                        .then(
                                   Commands.literal("white")
                                           .then(
                                                   Commands.literal("add")
                                                           .executes(SweepCommand::whiteAdd)

                                           )
                                           .then(
                                                   Commands.literal("del")
                                                           .executes(SweepCommand::whiteDel)
                                           )
                        )

                        .requires(context -> context.hasPermission(2))

        );
    }

    private static int whiteAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var itemStack = player.getMainHandItem();
        if (ForgeRegistries.ITEMS.getKey(itemStack.getItem()) != null) {
            Static.config.getItemsClean().addItemEntitiesWhitelist(ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
            ConfigHandler.onChange();
            player.sendSystemMessage(Component.literal("已经添加到白名单"));
        } else {
            player.sendSystemMessage(Component.literal("添加到白名单失败"));

        }
        return 1;
    }

    private static int whiteDel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var itemStack = player.getMainHandItem();
        if (ForgeRegistries.ITEMS.getKey(itemStack.getItem()) != null) {
            Static.config.getItemsClean().delItemEntitiesWhitelist(ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
            ConfigHandler.onChange();
            player.sendSystemMessage(Component.literal("已经从白名单移除"));
        } else {
            player.sendSystemMessage(Component.literal("从白名单移除失败"));
        }
        return 1;
    }

    private static int itemsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupItemEntity(world);
        return 1;
    }

    private static int monstersExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupMonsterEntity(world);
        return 1;
    }

    private static int animalsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupAnimalEntity(world);
        return 1;
    }

    private static int xpsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupXpEntity(world);
        return 1;
    }

    private static int othersExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanOtherEntities(world);
        return 1;
    }

}

package nova.committee.atom.sweep.common.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
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
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
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
        ServerWorld world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupItemEntity(world);
        return 1;
    }

    private static int monstersExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupMonsterEntity(world);
        return 1;
    }

    private static int animalsExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupAnimalEntity(world);
        return 1;
    }

    private static int xpsExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanupXpEntity(world);
        return 1;
    }

    private static int othersExe(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getLevel();
        Sweeper.INSTANCE.cleanOtherEntities(world);
        return 1;
    }

}

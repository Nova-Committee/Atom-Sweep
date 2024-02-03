package committee.nova.atom.sweep.common.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import committee.nova.atom.sweep.Static;
import committee.nova.atom.sweep.core.Sweeper;
import committee.nova.atom.sweep.init.config.ModConfig;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;


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
                                        .requires(context -> Static.cmdPermission(context, "atom.sweep.command.items", true))
                        )
                        .then(
                                Commands.literal("monsters")
                                        .executes(SweepCommand::monstersExe)
                                        .requires(context -> Static.cmdPermission(context, "atom.sweep.command.monsters", true))
                        )
                        .then(
                                Commands.literal("animals")
                                        .executes(SweepCommand::animalsExe)
                                        .requires(context -> Static.cmdPermission(context, "atom.sweep.command.animals", true))
                        )
                        .then(
                                Commands.literal("others")
                                        .executes(SweepCommand::othersExe)
                                        .requires(context -> Static.cmdPermission(context, "atom.sweep.command.others", true))
                        )
                        .then(
                                Commands.literal("xps")
                                        .executes(SweepCommand::xpsExe)
                                        .requires(context -> Static.cmdPermission(context, "atom.sweep.command.xps", true))
                        )
                        .then(
                                   Commands.literal("white")
                                           .then(
                                                   Commands.literal("item")
                                                           .then(
                                                                   Commands.literal("add")
                                                                           .executes(SweepCommand::itemWhiteAdd)
                                                                           .requires(context -> context.hasPermission(2))

                                                           )
                                                           .then(
                                                                   Commands.literal("del")
                                                                           .executes(SweepCommand::itemWhiteDel)
                                                                           .requires(context -> context.hasPermission(2))
                                                           )
                                           )
                                           .then(
                                                   Commands.literal("entity")
                                                           .then(
                                                                   Commands.literal("add")
                                                                           .then(Commands.argument("entity_name", ResourceLocationArgument.id())
                                                                                   .executes(SweepCommand::entityWhiteAdd)
                                                                           )
                                                                           .requires(context -> context.hasPermission(2))

                                                           )
                                                           .then(
                                                                   Commands.literal("del")
                                                                           .then(Commands.argument("entity_name", ResourceLocationArgument.id())
                                                                                   .executes(SweepCommand::entityWhiteDel)
                                                                           )
                                                                           .requires(context -> context.hasPermission(2))
                                                           )
                                           )

                        )
        );
    }

    private static int itemWhiteAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val player = context.getSource().getPlayerOrException();
        val itemStack = player.getMainHandItem();
        if (ForgeRegistries.ITEMS.getKey(itemStack.getItem()) != null) {
            ModConfig.INSTANCE.getItem().addItemEntitiesWhitelist(ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
            ModConfig.INSTANCE.save();
            Static.sendMessage(player, "message.cmd.item_white.add.success");
        } else {
            Static.sendMessage(player, "message.cmd.item_white.add.fail");
        }
        return 1;
    }

    private static int itemWhiteDel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val player = context.getSource().getPlayerOrException();
        val itemStack = player.getMainHandItem();
        if (ForgeRegistries.ITEMS.getKey(itemStack.getItem()) != null) {
            ModConfig.INSTANCE.getItem().delItemEntitiesWhitelist(ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
            ModConfig.INSTANCE.save();
            Static.sendMessage(player, "message.cmd.item_white.del.success");
        } else {
            Static.sendMessage(player, "message.cmd.item_white.del.success");
        }
        return 1;
    }

    private static int entityWhiteAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val player = context.getSource().getPlayerOrException();
        val entityName = context.getArgument("entity_name", ResourceLocation.class);
        //#if MC < 11900
        if (ForgeRegistries.ENTITIES.getValue(entityName) != null) {
            //#else
            //$$ if (ForgeRegistries.ENTITY_TYPES.getValue(entityName) != null) {
            //#endif
            ModConfig.INSTANCE.getMob().addMobEntitiesWhitelist(entityName.toString());
            ModConfig.INSTANCE.save();
            Static.sendMessage(player, "message.cmd.entity_white.add.success");
        } else {
            Static.sendMessage(player, "message.cmd.entity_white.add.fail");
        }
        return 1;
    }

    private static int entityWhiteDel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val player = context.getSource().getPlayerOrException();
        val entityName = context.getArgument("entity_name", ResourceLocation.class);
        //#if MC < 11900
        if (ForgeRegistries.ENTITIES.getValue(entityName) != null) {
            //#else
            //$$ if (ForgeRegistries.ENTITY_TYPES.getValue(entityName) != null) {
            //#endif
            ModConfig.INSTANCE.getItem().delItemEntitiesWhitelist(entityName.toString());
            ModConfig.INSTANCE.save();
            Static.sendMessage(player, "message.cmd.entity_white.del.success");
        } else {
            Static.sendMessage(player, "message.cmd.entity_white.del.success");
        }
        return 1;
    }

    private static int itemsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val world = context.getSource().getLevel();
        val killItemCount = Sweeper.INSTANCE.cleanupItemEntity(world);
        Static.sendMessageToAllPlayers(world.getServer(), ModConfig.INSTANCE.getCommon().getSweepNoticeComplete(),
                killItemCount, 0, 0, 0);

        return 1;
    }

    private static int monstersExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val world = context.getSource().getLevel();
        val killLivingCount = Sweeper.INSTANCE.cleanupMonsterEntity(world);

        Static.sendMessageToAllPlayers(world.getServer(), ModConfig.INSTANCE.getCommon().getSweepNoticeComplete(),
                0, killLivingCount, 0, 0);

        return 1;
    }

    private static int animalsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val world = context.getSource().getLevel();
        val killLivingCount = Sweeper.INSTANCE.cleanupAnimalEntity(world);
        Static.sendMessageToAllPlayers(world.getServer(), ModConfig.INSTANCE.getCommon().getSweepNoticeComplete(),
                0, killLivingCount, 0, 0);

        return 1;
    }

    private static int xpsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val world = context.getSource().getLevel();
        val killXpCount = Sweeper.INSTANCE.cleanupXpEntity(world);
        Static.sendMessageToAllPlayers(world.getServer(), ModConfig.INSTANCE.getCommon().getSweepNoticeComplete(),
                0, 0, killXpCount, 0);
        return 1;
    }

    private static int othersExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val world = context.getSource().getLevel();
        val killOtherCount = Sweeper.INSTANCE.cleanOtherEntities(world);
        Static.sendMessageToAllPlayers(world.getServer(), ModConfig.INSTANCE.getCommon().getSweepNoticeComplete(),
                0, 0, 0, killOtherCount);
        return 1;
    }

}

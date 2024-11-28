package committee.nova.mods.atom.sweep.common.core;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import committee.nova.mods.atom.sweep.common.Constants;
import committee.nova.mods.atom.sweep.common.config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 18:08
 * Version: 1.0
 */
public class SweepCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal(Constants.MOD_ID)
                .then(
                    Commands.literal("items")
                        .executes(SweepCommand::itemsExe)
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.items", true))
                )
                .then(
                    Commands.literal("monsters")
                        .executes(SweepCommand::monstersExe)
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.monsters", true))
                )
                .then(
                    Commands.literal("animals")
                        .executes(SweepCommand::animalsExe)
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.animals", true))
                )
                .then(
                    Commands.literal("others")
                        .executes(SweepCommand::othersExe)
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.others", true))
                )
                .then(
                    Commands.literal("xps")
                        .executes(SweepCommand::xpsExe)
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.xps", true))
                )
                .then(
                    Commands.literal("itemWhite")
                        .then(
                            Commands.literal("add")
                                .executes(SweepCommand::itemWhiteAdd)
                        )
                        .then(
                            Commands.literal("del")
                                .executes(SweepCommand::itemWhiteDel)
                        )
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.itemWhite", true))
                )
                .then(
                    Commands.literal("itemBlack")
                        .then(
                            Commands.literal("add")
                                .executes(SweepCommand::itemBlackAdd)
                        )
                        .then(
                            Commands.literal("del")
                                .executes(SweepCommand::itemBlackDel)
                        )
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.itemBlack", true))
                )
                .then(
                    Commands.literal("entityWhite")
                        .then(
                            Commands.literal("add")
                                .executes(SweepCommand::entityWhiteAdd)
                        )
                        .then(
                            Commands.literal("del")
                                .executes(SweepCommand::entityWhiteDel)
                        )
                        .requires(context -> Constants.cmdPermission(context, "atom.sweep.command.entityWhite", true))
                )
        );
    }

    private static int itemWhiteAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var itemStack = player.getMainHandItem();
        var registerName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString();
        List<String> list = new ArrayList<>(Config.COMMON.itemEntitiesWhitelist.get());
        list.add(registerName);
        Config.COMMON.itemEntitiesWhitelist.set(list);
        Config.COMMON.saveAsync();
        player.sendSystemMessage(Component.literal(registerName + "已经添加到白名单"));
        return 1;
    }

    private static int itemWhiteDel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var itemStack = player.getMainHandItem();
        var registerName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString();
        Config.COMMON.itemEntitiesWhitelist.get().remove(registerName);
        Config.COMMON.saveAsync();
        player.sendSystemMessage(Component.literal(registerName + "已经从白名单移除"));
        return 1;
    }

    private static int itemBlackAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var itemStack = player.getMainHandItem();
        var registerName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString();
        List<String> list = new ArrayList<>(Config.COMMON.itemEntitiesBlacklist.get());
        list.add(registerName);
        Config.COMMON.itemEntitiesBlacklist.set(list);
        Config.COMMON.saveAsync();
        player.sendSystemMessage(Component.literal(registerName + "已经添加到白名单"));
        return 1;
    }

    private static int itemBlackDel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var itemStack = player.getMainHandItem();
        var registerName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString();
        Config.COMMON.itemEntitiesBlacklist.get().remove(registerName);
        Config.COMMON.saveAsync();
        player.sendSystemMessage(Component.literal(registerName + "已经从白名单移除"));
        return 1;
    }

    private static boolean canBeTarget(Entity target, Entity viewEntity) {
        if (target.isRemoved()) {
            return false;
        }
        if (target == viewEntity.getVehicle()) {
            return false;
        }
        if (target instanceof Player || target instanceof ServerPlayer) {
            return false;
        }
        return true;
    }

    private static int entityWhiteAdd(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var traceStart = player.getEyePosition();
        var lookAngle = player.getLookAngle();
        var traceEnd = traceStart.add(lookAngle.x * 5, lookAngle.y * 5, lookAngle.z * 5);
        var aabb = new AABB(traceStart, traceEnd);
        var worldIn = player.level();

        var d0 = Double.MAX_VALUE;
        Entity entity = null;

        for (Entity entity1 : worldIn.getEntities(player, aabb, e -> canBeTarget(e, player))) {
            var axisalignedbb = entity1.getBoundingBox();
            if (axisalignedbb.getSize() < 0.3) {
                axisalignedbb = axisalignedbb.inflate(0.3);
            }
            if (axisalignedbb.contains(traceStart)) {
                entity = entity1;
                break;
            }
            var optional = axisalignedbb.clip(traceStart, traceEnd);
            if (optional.isPresent()) {
                var d1 = traceStart.distanceToSqr(optional.get());
                if (d1 < d0) {
                    entity = entity1;
                    d0 = d1;
                }
            }
        }

        if (entity == null) {
            player.sendSystemMessage(Component.literal("未找到实体"));
            return 0;
        } else {
            var registerName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
            List<String> list = new ArrayList<>(Config.COMMON.itemEntitiesWhitelist.get());
            list.add(registerName);
            Config.COMMON.mobEntitiesWhitelist.set(list);
            Config.COMMON.saveAsync();
            player.sendSystemMessage(Component.literal(registerName + "已经添加到白名单"));
        }
        return 1;
    }

    private static int entityWhiteDel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var traceStart = player.getEyePosition();
        var lookAngle = player.getLookAngle();
        var traceEnd = traceStart.add(lookAngle.x * 5, lookAngle.y * 5, lookAngle.z * 5);
        var aabb = new AABB(traceStart, traceEnd);
        var worldIn = player.level();

        var d0 = Double.MAX_VALUE;
        Entity entity = null;

        for (Entity entity1 : worldIn.getEntities(player, aabb, e -> canBeTarget(e, player))) {
            var axisalignedbb = entity1.getBoundingBox();
            if (axisalignedbb.getSize() < 0.3) {
                axisalignedbb = axisalignedbb.inflate(0.3);
            }
            if (axisalignedbb.contains(traceStart)) {
                entity = entity1;
                break;
            }
            var optional = axisalignedbb.clip(traceStart, traceEnd);
            if (optional.isPresent()) {
                var d1 = traceStart.distanceToSqr(optional.get());
                if (d1 < d0) {
                    entity = entity1;
                    d0 = d1;
                }
            }
        }

        if (entity == null) {
            player.sendSystemMessage(Component.literal("未找到实体"));
            return 0;
        } else {
            var registerName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
            Config.COMMON.mobEntitiesWhitelist.get().remove(registerName);
            Config.COMMON.saveAsync();
            player.sendSystemMessage(Component.literal(registerName + "已经从白名单移除"));
        }
        return 1;
    }

    private static int itemsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        var killItemCount = Sweeper.INSTANCE.cleanupItemEntity(world);
        Constants.sendMessageToAllPlayers(world.getServer(), Config.COMMON.sweepNoticeComplete.get(), killItemCount, 0, 0, 0);
        return 1;
    }

    private static int monstersExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        var killLivingCount = Sweeper.INSTANCE.cleanupMonsterEntity(world);
        Constants.sendMessageToAllPlayers(world.getServer(), Config.COMMON.sweepNoticeComplete.get(), 0, killLivingCount, 0, 0);
        return 1;
    }

    private static int animalsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        var killLivingCount = Sweeper.INSTANCE.cleanupAnimalEntity(world);
        Constants.sendMessageToAllPlayers(world.getServer(), Config.COMMON.sweepNoticeComplete.get(), 0, killLivingCount, 0, 0);
        return 1;
    }

    private static int xpsExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        var killXpCount = Sweeper.INSTANCE.cleanupXpEntity(world);
        Constants.sendMessageToAllPlayers(world.getServer(), Config.COMMON.sweepNoticeComplete.get(), 0, 0, killXpCount, 0);
        return 1;
    }

    private static int othersExe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var world = context.getSource().getLevel();
        var killOtherCount = Sweeper.INSTANCE.cleanOtherEntities(world);
        Constants.sendMessageToAllPlayers(world.getServer(), Config.COMMON.sweepNoticeComplete.get(), 0, 0, 0, killOtherCount);
        return 1;
    }
}

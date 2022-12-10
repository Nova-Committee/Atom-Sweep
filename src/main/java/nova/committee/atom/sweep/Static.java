package nova.committee.atom.sweep;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.util.Tristate;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;
import nova.committee.atom.sweep.init.config.SweepConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 8:57
 * Version: 1.0
 */
public class Static {
    public static final String MOD_ID = "atomsweep";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static Path CONFIG_FOLDER;
    public static SweepConfig config;
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static boolean isLuckPerms = false;


    public static void sendMessageToAllPlayers(Component message, boolean actionBar) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().getPlayers()
                .forEach(player -> player.displayClientMessage(message, actionBar)))).start();
    }


    public static void sendMessageToAllPlayers(MinecraftServer server1, String message, Object... args) {
        new Thread(() -> Optional.ofNullable(server1).ifPresent(server -> server.getPlayerList()
                .broadcastMessage(new TextComponent(String.format(message, args)), ChatType.SYSTEM, Util.NIL_UUID)))
                .start();
    }

    public static void sendMessageToAllPlayers(String message, Object... args) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
                .broadcastMessage(new TextComponent(String.format(message, args)), ChatType.SYSTEM, Util.NIL_UUID)))
                .start();
    }


    public static Boolean hasPermission(ServerPlayer playerEntity, String permission) throws CommandSyntaxException {
        AtomicReference<Boolean> exist = new AtomicReference<>(false);

        LuckPermsProvider.get().getUserManager().loadUser(playerEntity.getUUID())
                .thenApplyAsync(user -> {
                    CachedPermissionData permissionData = user.getCachedData()
                            .getPermissionData(user.getQueryOptions());
                    Tristate tristate = permissionData.checkPermission(permission);
                    if (tristate.equals(Tristate.UNDEFINED)) {
                        return false;
                    }

                    return tristate.asBoolean();
                }).thenAcceptAsync(aBoolean -> {
                    if (aBoolean)
                        exist.set(true);
                });

        return exist.get();
    }

    public static Boolean cmdPermission(CommandSourceStack source, String permission, boolean admin) {
        if (!Static.isLuckPerms)
            if (admin)
                return source.hasPermission(2);
            else
                return true;
        else
            try {
                return Static.hasPermission(source.getPlayerOrException(), permission);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
    }

}

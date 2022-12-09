package nova.committee.atom.sweep;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import nova.committee.atom.sweep.init.config.SweepConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

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


    public static void sendMessageToAllPlayers(Component message, boolean actionBar) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().getPlayers()
                .forEach(player -> player.displayClientMessage(message, actionBar)))).start();
    }


    public static void sendMessageToAllPlayers(MinecraftServer server1, String message, Object... args) {
        new Thread(() -> Optional.ofNullable(server1).ifPresent(server -> server.getPlayerList()
                .broadcastSystemMessage(Component.literal(String.format(message, args)), false)))
                .start();
    }

    public static void sendMessageToAllPlayers(String message, Object... args) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
                .broadcastSystemMessage(Component.literal(String.format(message, args)), false)))
                .start();
    }

}

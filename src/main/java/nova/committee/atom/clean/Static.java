package nova.committee.atom.clean;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import nova.committee.atom.clean.init.config.CleanerConfig;
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
    public static final String MOD_ID = "atomclean";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static Path CONFIG_FOLDER;
    public static CleanerConfig config;

    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();


    public static void sendMessageToAllPlayers(ITextComponent message, boolean actionBar) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().getPlayers()
                .forEach(player -> player.displayClientMessage(message, actionBar)))).start();
    }


}

package nova.committee.atom.sweep.init.handler;

import net.minecraft.server.MinecraftServer;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/9 14:50
 * Version: 1.0
 */
public class ServerEventHandler {

    public static void onServerAboutToStart(MinecraftServer server) {
        Static.SERVER = server;
    }

    public static void onServerStarted(MinecraftServer server) {
        Static.config = ConfigHandler.load();//读取配置
        Sweeper.INSTANCE.startSweep();

    }

    public static void onServerStopping(MinecraftServer server) {
        Sweeper.INSTANCE.stopSweep();
    }

    public static void onServerStopped(MinecraftServer server) {
        ConfigHandler.save(Static.config);
    }


}

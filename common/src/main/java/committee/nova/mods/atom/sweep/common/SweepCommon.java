package committee.nova.mods.atom.sweep.common;

import committee.nova.mods.atom.sweep.common.config.Config;
import committee.nova.mods.atom.sweep.common.core.Sweeper;
import net.minecraft.server.MinecraftServer;

public class SweepCommon {

    public static int counter = -1;

    public static void init() {
    }


    public static void onServerAboutToStart(MinecraftServer server) {
        Constants.SERVER = server;
    }

    public static void onServerStarted(MinecraftServer server) {
        Sweeper.INSTANCE.startSweep();
    }

    public static void onServerStopping(MinecraftServer server) {
        Sweeper.INSTANCE.stopSweep();
    }

    public static void onServerTick(MinecraftServer server) {
        if (counter >= 0) {
            if (counter == 0) {
                Sweeper.INSTANCE.sweep(server);
                counter = -1;
            } else {
                if (counter % 20 == 0) {
                    Constants.sendMessageToAllPlayers(Config.COMMON.sweepNotice.get(), counter / 20);
                }

                --counter;
            }
        }
    }
}
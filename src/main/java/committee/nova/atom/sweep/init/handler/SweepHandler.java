package committee.nova.atom.sweep.init.handler;


import committee.nova.atom.sweep.init.config.ModConfig;
import net.minecraft.server.MinecraftServer;
import committee.nova.atom.sweep.Static;
import committee.nova.atom.sweep.core.Sweeper;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:00
 * Version: 1.0
 */
public class SweepHandler {


    private static int counter = -1;

    public static void beginSweepCountDown() {
        counter = ModConfig.INSTANCE.getCommon().getSweepDiscount() * 20;
    }

    public static void onServerTick(MinecraftServer server) {
                if (counter >= 0) {
                    if (counter == 0) {
                        Sweeper.INSTANCE.sweep(server);
                        counter = -1;
                    } else {
                        if (counter % 20 == 0) {
                            Static.sendMessageToAllPlayers(ModConfig.INSTANCE.getCommon().getSweepNotice(), counter / 20);
                        }

                        --counter;
                    }
                }

    }

}

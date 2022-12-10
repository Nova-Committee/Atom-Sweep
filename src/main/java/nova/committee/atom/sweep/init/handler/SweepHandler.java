package nova.committee.atom.sweep.init.handler;


import net.minecraft.server.MinecraftServer;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:00
 * Version: 1.0
 */
public class SweepHandler {


    private static int counter = -1;

    public static void beginSweepCountDown() {
        counter = Static.config.getCommon().getSweepDiscount() * 20;
    }

    public static void onServerTick(MinecraftServer server) {

            if (counter >= 0) {
                if (counter == 0) {
                    Sweeper.INSTANCE.sweep(server);
                    counter = -1;
                } else {
                    if (counter % 20 == 0) {
                        Static.sendMessageToAllPlayers(Static.config.getCommon().getSweepNotice(), counter / 20);
                    }

                    --counter;
                }
            }

    }

}

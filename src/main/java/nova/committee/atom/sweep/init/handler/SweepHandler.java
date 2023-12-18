package nova.committee.atom.sweep.init.handler;


import net.minecraftforge.event.TickEvent;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;
import nova.committee.atom.sweep.init.config.ModConfig;

import java.util.Optional;

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

    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Optional.ofNullable(Static.SERVER).ifPresent(server -> {
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
            });
        }
    }

}

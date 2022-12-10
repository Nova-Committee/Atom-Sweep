package nova.committee.atom.sweep.init.handler;


import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;

import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:00
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SweepHandler {


    private static int counter = -1;

    public static void beginSweepCountDown() {
        counter = Static.config.getCommon().getSweepDiscount() * 20;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Optional.ofNullable(Static.SERVER).ifPresent(server -> {
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
            });
        }
    }

}

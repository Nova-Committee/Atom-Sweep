package nova.committee.atom.sweep.init.handler;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.sweep.common.cmd.SweepCommand;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 16:15
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CmdHandler {

    @SubscribeEvent
    public static void registryCmd(RegisterCommandsEvent event) {
        SweepCommand.register(event.getDispatcher());
    }
}

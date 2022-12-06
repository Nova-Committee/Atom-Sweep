package nova.committee.atom.clean.init.handler;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.clean.common.cmd.CleanCommand;

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
        CleanCommand.register(event.getDispatcher());
    }
}

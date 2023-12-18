package nova.committee.atom.sweep.init.handler;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.core.Sweeper;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/9 14:50
 * Version: 1.0
 */
@Mod.EventBusSubscriber(modid = Static.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        Static.SERVER = event.getServer();
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        Static.config = ConfigHandler.load();//读取配置
        Sweeper.INSTANCE.startSweep();

    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        ConfigHandler.save(Static.config);
        Sweeper.INSTANCE.stopSweep();
    }

}

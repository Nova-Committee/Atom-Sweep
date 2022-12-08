package nova.committee.atom.sweep.init.handler;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
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
    public static void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        Static.SERVER = event.getServer();
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        Static.config = ConfigHandler.load();//读取配置
        Sweeper.INSTANCE.startSweep();

    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event) {
        Sweeper.INSTANCE.stopSweep();
    }

    @SubscribeEvent
    public static void onServerStopped(FMLServerStoppedEvent event) {
        ConfigHandler.save(Static.config);
    }


}

package nova.committee.atom.clean.init.handler;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import nova.committee.atom.clean.Static;


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
    }
    @SubscribeEvent
    public static void onServerStopped(FMLServerStoppedEvent event) {
        ConfigHandler.save(Static.config);
    }


}

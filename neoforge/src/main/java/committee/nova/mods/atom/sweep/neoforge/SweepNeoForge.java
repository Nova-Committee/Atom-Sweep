package committee.nova.mods.atom.sweep.neoforge;


import committee.nova.mods.atom.sweep.common.Constants;
import committee.nova.mods.atom.sweep.common.SweepCommon;
import committee.nova.mods.atom.sweep.common.core.SweepCommand;
import fuzs.forgeconfigapiport.neoforge.api.forge.v4.ForgeConfigRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import committee.nova.mods.atom.sweep.common.config.Config;

@Mod(Constants.MOD_ID)
@EventBusSubscriber
public class SweepNeoForge {

    public SweepNeoForge(IEventBus eventBus) {
        SweepCommon.init();
        Config.initCommonConfig(spec->{
            ForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, spec);
        });
    }

    @SubscribeEvent
    public static void cmdRegister(RegisterCommandsEvent event) {
        SweepCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        SweepCommon.onServerAboutToStart(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        SweepCommon.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        SweepCommon.onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        SweepCommon.onServerTick(event.getServer());
    }
}
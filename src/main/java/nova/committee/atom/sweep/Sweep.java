package nova.committee.atom.sweep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import nova.committee.atom.sweep.init.handler.ServerEventHandler;
import nova.committee.atom.sweep.init.handler.SweepHandler;
import nova.committee.atom.sweep.util.FileUtils;

import static nova.committee.atom.sweep.Static.CONFIG_FOLDER;

/**
 * Project: AtomSweep-fabric
 * Author: cnlimiter
 * Date: 2022/12/11 0:18
 * Description:
 */
public class Sweep implements ModInitializer {
    @Override
    public void onInitialize() {


        Static.isLuckPerms = FabricLoader.getInstance().isModLoaded("luckperms");
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            CONFIG_FOLDER = FabricLoader.getInstance().getGameDir().resolve("atom");
            FileUtils.checkFolder(CONFIG_FOLDER);
            ServerEventHandler.onServerAboutToStart(server);
        });
        ServerLifecycleEvents.SERVER_STARTED.register(ServerEventHandler::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(ServerEventHandler::onServerStopping);
        ServerLifecycleEvents.SERVER_STOPPED.register(ServerEventHandler::onServerStopped);
        ServerTickEvents.END_SERVER_TICK.register(SweepHandler::onServerTick);
    }
}

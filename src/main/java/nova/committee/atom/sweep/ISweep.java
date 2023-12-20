package nova.committee.atom.sweep;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import nova.committee.atom.sweep.core.Sweeper;
import nova.committee.atom.sweep.init.handler.SweepHandler;
import nova.committee.atom.sweep.util.FileUtils;

import static nova.committee.atom.sweep.Static.ATOM_FOLDER;
import static nova.committee.atom.sweep.Static.SWEEP_FOLDER;

public class ISweep {
    public ISweep() {
        ATOM_FOLDER = FMLPaths.GAMEDIR.get().resolve("atom");
        FileUtils.checkFolder(ATOM_FOLDER);
        SWEEP_FOLDER = ATOM_FOLDER.resolve("sweep");
        FileUtils.checkFolder(SWEEP_FOLDER);
        Static.CONFIG_FILE = SWEEP_FOLDER.resolve("config.toml");
        Static.isLuckPerms = ModList.get().isLoaded("luckperms");
    }


    public void onServerStarting(MinecraftServer server) {
        Static.SERVER = server;//获取服务器实例
    }

    public void onServerStarted(MinecraftServer server) {
        Sweeper.INSTANCE.startSweep();
    }

    public void onServerStopping(MinecraftServer server) {

    }

    public void onServerStopped(MinecraftServer server) {
        Sweeper.INSTANCE.stopSweep();
    }

    public void onServerTick(TickEvent.ServerTickEvent event) {
        SweepHandler.onServerTick(event);
    }

}

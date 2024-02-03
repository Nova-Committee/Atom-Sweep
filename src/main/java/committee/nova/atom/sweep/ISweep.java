package committee.nova.atom.sweep;

import committee.nova.atom.sweep.core.Sweeper;
import committee.nova.atom.sweep.util.FileUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import committee.nova.atom.sweep.init.handler.SweepHandler;

public class ISweep {
    public ISweep() {
        Static.ATOM_FOLDER = FMLPaths.GAMEDIR.get().resolve("atom");
        FileUtils.checkFolder(Static.ATOM_FOLDER);
        Static.SWEEP_FOLDER = Static.ATOM_FOLDER.resolve("sweep");
        FileUtils.checkFolder(Static.SWEEP_FOLDER);
        Static.CONFIG_FILE = Static.SWEEP_FOLDER.resolve("config.toml");
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

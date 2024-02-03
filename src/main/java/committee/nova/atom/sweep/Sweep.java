package committee.nova.atom.sweep;

import committee.nova.atom.sweep.common.cmd.SweepCommand;
import committee.nova.atom.sweep.init.handler.SweepHandler;
import committee.nova.atom.sweep.lib.FabricClasspathAppender;
import committee.nova.atom.sweep.util.FileUtils;
import dev.vankka.dependencydownload.DependencyManager;
import dev.vankka.dependencydownload.dependency.StandardDependency;
import dev.vankka.dependencydownload.repository.StandardRepository;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.minecraft.server.MinecraftServer;
import committee.nova.atom.sweep.core.Sweeper;
//#if MC < 11900
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//#else
//$$ import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//#endif
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static committee.nova.atom.sweep.Static.ATOM_FOLDER;
import static committee.nova.atom.sweep.Static.SWEEP_FOLDER;

public class Sweep implements ModInitializer {

    @Override
    public void onInitialize() {
        ATOM_FOLDER = FabricLoader.getInstance().getGameDir().resolve("atom");
        FileUtils.checkFolder(ATOM_FOLDER);

        Path cache = ATOM_FOLDER.resolve("cache");
        FileUtils.checkFolder(cache);
        DependencyManager manager = new DependencyManager(cache);
        try {
            manager.loadFromResource(FabricLauncherBase.getLauncher().getTargetClassLoader().getResource("lib.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Executor executor = Executors.newCachedThreadPool();
        manager.downloadAll(executor, Collections.singletonList(new StandardRepository("https://repo1.maven.org/maven2"))).join();
        manager.loadAll(executor, new FabricClasspathAppender()).join();


        SWEEP_FOLDER = ATOM_FOLDER.resolve("sweep");
        FileUtils.checkFolder(SWEEP_FOLDER);
        Static.CONFIG_FILE = SWEEP_FOLDER.resolve("config.toml");
        Static.isLuckPerms = FabricLoader.getInstance().isModLoaded("luckperms");

        //#if MC < 11900
        CommandRegistrationCallback.EVENT.register((dispatcher, environment) -> SweepCommand.register(dispatcher));
        //#else
        //$$ CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SweepCommand.register(dispatcher));
        //#endif

        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);

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

    public void onServerTick(MinecraftServer server) {
        SweepHandler.onServerTick(server);
    }


}

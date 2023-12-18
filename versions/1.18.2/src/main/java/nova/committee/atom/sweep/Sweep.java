package nova.committee.atom.sweep;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.atom.sweep.common.cmd.SweepCommand;
import org.jetbrains.annotations.NotNull;

@Mod(Static.MOD_ID)
public class Sweep {

    public ISweep sweep;

    public Sweep() {
        this.sweep = new ISweep();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void cmdRegister(@NotNull RegisterCommandsEvent event) {
        SweepCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerAboutToStart(@NotNull ServerAboutToStartEvent event) {
        this.sweep.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void onServerStarted(@NotNull ServerStartedEvent event) {
        this.sweep.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        this.sweep.onServerTick(event);
    }

    @SubscribeEvent
    public void onServerStopping(@NotNull ServerStoppedEvent event) {
        this.sweep.onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopped(@NotNull ServerStoppedEvent event) {
        this.sweep.onServerStopped(event.getServer());
    }


}

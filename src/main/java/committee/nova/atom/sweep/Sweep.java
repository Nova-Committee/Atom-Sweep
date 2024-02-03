package committee.nova.atom.sweep;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import committee.nova.atom.sweep.common.cmd.SweepCommand;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
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
    public void onServerAboutToStart(@NotNull FMLServerAboutToStartEvent event) {
        this.sweep.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void onServerStarted(@NotNull FMLServerStartedEvent event) {
        this.sweep.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        this.sweep.onServerTick(event);
    }

    @SubscribeEvent
    public void onServerStopping(@NotNull FMLServerStoppedEvent event) {
        this.sweep.onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopped(@NotNull FMLServerStoppedEvent event) {
        this.sweep.onServerStopped(event.getServer());
    }


}

package nova.committee.atom.sweep.init.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;

/**
 * Project: clean
 * Author: cnlimiter
 * Date: 2022/12/9 0:42
 * Description:
 */
public class SweepEntityEvent{
    public SweepEntityEvent(){}

    public static final Event<SweepEntityEvent.SweepEvent> SWEEP_EVENT = EventFactory.createArrayBacked(SweepEntityEvent.SweepEvent.class, callbacks -> (entity) -> {
        for (SweepEntityEvent.SweepEvent callback : callbacks) {
            if (!callback.onSweep(entity)){
                return false;
            }
        }
        return true;
    });

    @FunctionalInterface
    public interface SweepEvent {
        boolean onSweep(Entity entity);
    }

}

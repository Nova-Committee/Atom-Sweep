package nova.committee.atom.sweep.init.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

/**
 * Project: clean
 * Author: cnlimiter
 * Date: 2022/12/9 0:42
 * Description:
 */
public class SweepEntityEvent extends Event {
    private final Entity entity;

    public SweepEntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}

package nova.committee.atom.sweep.core.model;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import nova.committee.atom.sweep.Static;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:03
 * Version: 1.0
 */
public class AESMobEntity {
    private final Mob entity;
    private final ResourceLocation registryName;

    public AESMobEntity(Mob entity) {
        this.entity = entity;
        this.registryName = EntityType.getKey(entity.getType());
    }

    public boolean filtrate() {
        int index;
        if (Static.config.getMobsClean().isMobEntityCleanupEnable()) {
            // Whitelist
            for (String s : Static.config.getMobsClean().getMobEntitiesWhitelist()) {
                if (s.equals(this.registryName.toString())) {
                    return false;
                } else if ((index = s.indexOf('*')) != -1) {
                    s = s.substring(0, index - 1);
                    if (this.registryName.getNamespace().equals(s)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            // Blacklist
            for (String s : Static.config.getMobsClean().getMobEntitiesBlacklist()) {
                if (AESItemEntity.itemMatch(s, this.registryName)) return true;
            }
            return false;
        }
    }

    public Mob getEntity() {
        return entity;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

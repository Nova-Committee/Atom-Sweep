package nova.committee.atom.sweep.core.model;


import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;
import nova.committee.atom.sweep.Static;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 14:58
 * Version: 1.0
 */
public class AESItemEntity {
    private final ItemEntity entity;
    private final ResourceLocation registryName;

    public AESItemEntity(ItemEntity entity) {
        this.entity = entity;
        this.registryName = this.entity.getItem().getItem().getRegistryName();
    }

    /**
     * @return 白名单或者黑名单
     */
    public boolean filtrate() {
        if (Static.config.getItemsClean().isItemEntitiesMatchMode()) {
            // Whitelist
            for (String s : Static.config.getItemsClean().getItemEntitiesWhitelist()) {
                if (itemMatch(s, this.registryName)) return false;
            }
            return true;
        } else {
            // Blacklist
            for (String s : Static.config.getItemsClean().getItemEntitiesBlacklist()) {
                if (itemMatch(s, this.registryName)) return true;
            }
            return false;
        }
    }

    static boolean itemMatch(String s, ResourceLocation registryName) {
        int index;
        if (s.equals(registryName.toString())) {
            return true;
        } else if ((index = s.indexOf('*')) != -1) {
            s = s.substring(0, index - 1);
            return registryName.getNamespace().equals(s);
        }
        return false;
    }

    public Entity getEntity() {
        return entity;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

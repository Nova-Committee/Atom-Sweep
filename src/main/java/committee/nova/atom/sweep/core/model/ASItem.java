package committee.nova.atom.sweep.core.model;


import committee.nova.atom.sweep.init.config.ModConfig;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
//#if MC < 12000
import net.minecraft.core.Registry;
//#else
//$$ import net.minecraft.core.registries.BuiltInRegistries;
//#endif
/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 14:58
 * Version: 1.0
 */
public class ASItem {
    private final ItemEntity entity;
    private final ResourceLocation registryName;

    public ASItem(ItemEntity entity) {
        this.entity = entity;
        //#if MC <= 12000
        this.registryName = Registry.ITEM.getKey(this.entity.getItem().getItem());
        //#else
        //$$ this.registryName = BuiltInRegistries.ITEM.getKey(this.entity.getItem().getItem());
        //#endif
    }

    /**
     * @return 白名单或者黑名单
     */
    public boolean filtrate() {
        if (ModConfig.INSTANCE.getItem().isItemWhiteMode()) {
            // Whitelist
            for (String s : ModConfig.INSTANCE.getItem().getItemEntitiesWhitelist()) {
                if (itemMatch(s, this.registryName)) return false;
            }
            return true;
        }

        if (ModConfig.INSTANCE.getItem().isItemBlackMode()) {
            // Blacklist
            for (String s : ModConfig.INSTANCE.getItem().getItemEntitiesBlacklist()) {
                if (itemMatch(s, this.registryName)) return true;
            }
            return false;
        }

        return true;
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

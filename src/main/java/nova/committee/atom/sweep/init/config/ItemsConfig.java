package nova.committee.atom.sweep.init.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import cn.evole.libs.tomlj.TomlTable;

import java.util.Arrays;
import java.util.List;

/**
 * Name: atomsweep / ItemCOnfig
 * Author: cnlimiter
 * CreateTime: 2023/12/19 3:15
 * Description:
 */

public class ItemsConfig extends AutoLoadTomlConfig {
    @TableField(rightComment = "物品实体清理功能")
    private boolean isItemEntityCleanupEnable = true;
    @TableField(rightComment = "白名单模式")
    private boolean itemWhiteMode = true;
    @TableField(rightComment = "黑名单模式")
    private boolean itemBlackMode = true;
    @TableField(rightComment = "白名单")
    private List<String> itemEntitiesWhitelist = Arrays.asList("minecraft:diamond", "minecraft:emerald");
    @TableField(rightComment = "黑名单")
    private List<String> itemEntitiesBlacklist = List.of();

    public ItemsConfig() {
        super(null);
    }

    public ItemsConfig(TomlTable source) {
        super(source);
        this.load(ItemsConfig.class);
    }


    public boolean isItemEntityCleanupEnable() {
        return isItemEntityCleanupEnable;
    }

    public void setItemEntityCleanupEnable(boolean itemEntityCleanupEnable) {
        isItemEntityCleanupEnable = itemEntityCleanupEnable;
    }

    public boolean isItemWhiteMode() {
        return itemWhiteMode;
    }

    public void setItemWhiteMode(boolean itemWhiteMode) {
        this.itemWhiteMode = itemWhiteMode;
    }

    public boolean isItemBlackMode() {
        return itemBlackMode;
    }

    public void setItemBlackMode(boolean itemBlackMode) {
        this.itemBlackMode = itemBlackMode;
    }

    public List<String> getItemEntitiesWhitelist() {
        return itemEntitiesWhitelist;
    }

    public void setItemEntitiesWhitelist(List<String> itemEntitiesWhitelist) {
        this.itemEntitiesWhitelist = itemEntitiesWhitelist;
    }

    public List<String> getItemEntitiesBlacklist() {
        return itemEntitiesBlacklist;
    }

    public void setItemEntitiesBlacklist(List<String> itemEntitiesBlacklist) {
        this.itemEntitiesBlacklist = itemEntitiesBlacklist;
    }

    public void addItemEntitiesWhitelist(String name) {
        this.itemEntitiesWhitelist.add(name);
    }

    public void delItemEntitiesWhitelist(String name) {
        this.itemEntitiesWhitelist.remove(name);
    }

}

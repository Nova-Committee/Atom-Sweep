package nova.committee.atom.sweep.init.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import cn.evole.libs.tomlj.TomlTable;

import java.util.Arrays;
import java.util.List;

/**
 * Name: atomsweep / MobConfig
 * Author: cnlimiter
 * CreateTime: 2023/12/19 3:15
 * Description:
 */

public class MobsConfig extends AutoLoadTomlConfig {
    @TableField(rightComment = "开启q群功能")
    private boolean isAnimalEntitiesCleanupEnable = true;
    @TableField(rightComment = "开启q群功能")
    private boolean isMonsterEntitiesCleanupEnable = true;
    @TableField(rightComment = "开启q群功能")
    private boolean isMobEntityCleanupEnable = true;
    @TableField(rightComment = "开启q群功能")
    private boolean mobEntitiesMatchMode = true;
    @TableField(rightComment = "开启q群功能")
    private List<String> mobEntitiesWhitelist = Arrays.asList("minecraft:cat", "minecraft:mule", "minecraft:wolf", "minecraft:horse",
            "minecraft:donkey", "minecraft:wither", "minecraft:guardian", "minecraft:villager", "minecraft:iron_golem", "minecraft:snow_golem",
            "minecraft:vindicator", "minecraft:ender_dragon", "minecraft:elder_guardian");
    @TableField(rightComment = "开启q群功能")
    private List<String> mobEntitiesBlacklist = List.of();

    public boolean isAnimalEntitiesCleanupEnable() {
        return isAnimalEntitiesCleanupEnable;
    }

    public void setAnimalEntitiesCleanupEnable(boolean animalEntitiesCleanupEnable) {
        isAnimalEntitiesCleanupEnable = animalEntitiesCleanupEnable;
    }

    public boolean isMonsterEntitiesCleanupEnable() {
        return isMonsterEntitiesCleanupEnable;
    }

    public void setMonsterEntitiesCleanupEnable(boolean monsterEntitiesCleanupEnable) {
        isMonsterEntitiesCleanupEnable = monsterEntitiesCleanupEnable;
    }

    public boolean isMobEntityCleanupEnable() {
        return isMobEntityCleanupEnable;
    }

    public void setMobEntityCleanupEnable(boolean mobEntityCleanupEnable) {
        isMobEntityCleanupEnable = mobEntityCleanupEnable;
    }

    public boolean isMobEntitiesMatchMode() {
        return mobEntitiesMatchMode;
    }

    public void setMobEntitiesMatchMode(boolean mobEntitiesMatchMode) {
        this.mobEntitiesMatchMode = mobEntitiesMatchMode;
    }

    public List<String> getMobEntitiesWhitelist() {
        return mobEntitiesWhitelist;
    }

    public void setMobEntitiesWhitelist(List<String> mobEntitiesWhitelist) {
        this.mobEntitiesWhitelist = mobEntitiesWhitelist;
    }

    public List<String> getMobEntitiesBlacklist() {
        return mobEntitiesBlacklist;
    }

    public void setMobEntitiesBlacklist(List<String> mobEntitiesBlacklist) {
        this.mobEntitiesBlacklist = mobEntitiesBlacklist;
    }

    public void addMobEntitiesWhitelist(String name) {
        this.mobEntitiesWhitelist.add(name);
    }

    public void delMobEntitiesWhitelist(String name) {
        this.mobEntitiesWhitelist.remove(name);
    }

    public MobsConfig() {
        super(null);
    }

    public MobsConfig(TomlTable source) {
        super(source);
        this.load(MobsConfig.class);
    }
}

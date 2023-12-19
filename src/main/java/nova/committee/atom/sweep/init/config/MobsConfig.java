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
    @TableField(rightComment = "生物实体清理功能（最高优先级）")
    private boolean isMobEntityCleanupEnable = true;
    @TableField(rightComment = "动物实体清理功能（次级）")
    private boolean isAnimalEntitiesCleanupEnable = true;
    @TableField(rightComment = "怪物实体清理功能（次级）")
    private boolean isMonsterEntitiesCleanupEnable = true;
    @TableField(rightComment = "白名单模式")
    private boolean mobWhiteMode = true;
    @TableField(rightComment = "黑名单模式")
    private boolean mobBlackMode = true;
    @TableField(rightComment = "生物白名单")
    private List<String> mobEntitiesWhitelist = Arrays.asList("minecraft:cat", "minecraft:mule", "minecraft:wolf", "minecraft:horse",
            "minecraft:donkey", "minecraft:wither", "minecraft:guardian", "minecraft:villager", "minecraft:iron_golem", "minecraft:snow_golem",
            "minecraft:vindicator", "minecraft:ender_dragon", "minecraft:elder_guardian");
    @TableField(rightComment = "生物黑名单")
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

    public boolean isMobBlackMode() {
        return mobBlackMode;
    }

    public void setMobBlackMode(boolean mobBlackMode) {
        this.mobBlackMode = mobBlackMode;
    }

    public boolean isMobWhiteMode() {
        return mobWhiteMode;
    }

    public void setMobWhiteMode(boolean mobWhiteMode) {
        this.mobWhiteMode = mobWhiteMode;
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

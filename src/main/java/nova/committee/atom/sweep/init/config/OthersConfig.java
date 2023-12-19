package nova.committee.atom.sweep.init.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import cn.evole.libs.tomlj.TomlTable;

/**
 * Name: atomsweep / OthersConfig
 * Author: cnlimiter
 * CreateTime: 2023/12/19 3:15
 * Description:
 */

public class OthersConfig extends AutoLoadTomlConfig {
    @TableField(rightComment = "经验球实体清理功能")
    private boolean isExperienceOrbEntityCleanupEnable = true;
    @TableField(rightComment = "下落方块实体清理功能")
    private boolean isFallingBlocksEntityCleanupEnable = true;
    @TableField(rightComment = "箭头实体清理功能")
    private boolean isArrowEntityCleanupEnable = true;
    @TableField(rightComment = "三叉戟实体清理功能")
    private boolean isTridentEntityCleanupEnable = false;
    @TableField(rightComment = "投射物实体清理功能")
    private boolean isDamagingProjectileEntityCleanupEnable = false;
    @TableField(rightComment = "子弹实体清理功能")
    private boolean isShulkerBulletEntityCleanupEnable = true;
    @TableField(rightComment = "烟花火箭实体清理功能")
    private boolean isFireworkRocketEntityCleanupEnable = false;
    @TableField(rightComment = "物品框实体清理功能")
    private boolean isItemFrameEntityCleanupEnable = false;
    @TableField(rightComment = "画实体清理功能")
    private boolean isPaintingEntityCleanupEnable = false;
    @TableField(rightComment = "船实体清理功能")
    private boolean isBoatEntityCleanupEnable = false;
    @TableField(rightComment = "TNT实体清理功能")
    private boolean isTNTEntityCleanupEnable = true;

    public boolean isExperienceOrbEntityCleanupEnable() {
        return isExperienceOrbEntityCleanupEnable;
    }

    public void setExperienceOrbEntityCleanupEnable(boolean experienceOrbEntityCleanupEnable) {
        isExperienceOrbEntityCleanupEnable = experienceOrbEntityCleanupEnable;
    }

    public boolean isFallingBlocksEntityCleanupEnable() {
        return isFallingBlocksEntityCleanupEnable;
    }

    public void setFallingBlocksEntityCleanupEnable(boolean fallingBlocksEntityCleanupEnable) {
        isFallingBlocksEntityCleanupEnable = fallingBlocksEntityCleanupEnable;
    }

    public boolean isArrowEntityCleanupEnable() {
        return isArrowEntityCleanupEnable;
    }

    public void setArrowEntityCleanupEnable(boolean arrowEntityCleanupEnable) {
        isArrowEntityCleanupEnable = arrowEntityCleanupEnable;
    }

    public boolean isTridentEntityCleanupEnable() {
        return isTridentEntityCleanupEnable;
    }

    public void setTridentEntityCleanupEnable(boolean tridentEntityCleanupEnable) {
        isTridentEntityCleanupEnable = tridentEntityCleanupEnable;
    }

    public boolean isDamagingProjectileEntityCleanupEnable() {
        return isDamagingProjectileEntityCleanupEnable;
    }

    public void setDamagingProjectileEntityCleanupEnable(boolean damagingProjectileEntityCleanupEnable) {
        isDamagingProjectileEntityCleanupEnable = damagingProjectileEntityCleanupEnable;
    }

    public boolean isShulkerBulletEntityCleanupEnable() {
        return isShulkerBulletEntityCleanupEnable;
    }

    public void setShulkerBulletEntityCleanupEnable(boolean shulkerBulletEntityCleanupEnable) {
        isShulkerBulletEntityCleanupEnable = shulkerBulletEntityCleanupEnable;
    }

    public boolean isFireworkRocketEntityCleanupEnable() {
        return isFireworkRocketEntityCleanupEnable;
    }

    public void setFireworkRocketEntityCleanupEnable(boolean fireworkRocketEntityCleanupEnable) {
        isFireworkRocketEntityCleanupEnable = fireworkRocketEntityCleanupEnable;
    }

    public boolean isItemFrameEntityCleanupEnable() {
        return isItemFrameEntityCleanupEnable;
    }

    public void setItemFrameEntityCleanupEnable(boolean itemFrameEntityCleanupEnable) {
        isItemFrameEntityCleanupEnable = itemFrameEntityCleanupEnable;
    }

    public boolean isPaintingEntityCleanupEnable() {
        return isPaintingEntityCleanupEnable;
    }

    public void setPaintingEntityCleanupEnable(boolean paintingEntityCleanupEnable) {
        isPaintingEntityCleanupEnable = paintingEntityCleanupEnable;
    }

    public boolean isBoatEntityCleanupEnable() {
        return isBoatEntityCleanupEnable;
    }

    public void setBoatEntityCleanupEnable(boolean boatEntityCleanupEnable) {
        isBoatEntityCleanupEnable = boatEntityCleanupEnable;
    }

    public boolean isTNTEntityCleanupEnable() {
        return isTNTEntityCleanupEnable;
    }

    public void setTNTEntityCleanupEnable(boolean TNTEntityCleanupEnable) {
        isTNTEntityCleanupEnable = TNTEntityCleanupEnable;
    }

    public OthersConfig() {
        super(null);
    }

    public OthersConfig(TomlTable source) {
        super(source);
        this.load(OthersConfig.class);
    }
}

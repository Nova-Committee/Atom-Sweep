package nova.committee.atom.sweep.init.config;

import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 21:38
 * Version: 1.0
 */
public class SweepConfig {
    private final String configName;

    public SweepConfig() {
        this.configName = "atom_sweep";
    }

    public String getConfigName() {
        return configName;
    }

    @SerializedName("items_clean")
    private ItemEntitiesClean itemsClean = new ItemEntitiesClean();
    @SerializedName("mobs_clean")
    private MobsClean mobsClean = new MobsClean();
    @SerializedName("others_clean")
    private OtherEntitiesClean othersClean = new OtherEntitiesClean();
    @SerializedName("common")
    private Common common = new Common();


    public ItemEntitiesClean getItemsClean() {
        return itemsClean;
    }

    public MobsClean getMobsClean() {
        return mobsClean;
    }

    public OtherEntitiesClean getOthersClean() {
        return othersClean;
    }

    public Common getCommon() {
        return common;
    }

    public static class Common {
        @Expose
        private int sweepPeriod = 4;
        @Expose
        private int sweepNotify = 20;
        @Expose
        private int sweepDiscount = 5;
        @Expose
        private String sweepNotice = "<演变> 注意：还有 %d 秒就要去你家吃饭了~";
        @Expose
        private String sweepNoticeComplete = "<演变> 这次一共吃掉了 %d 个掉落物， %d 个生物 %d 个经验球和 %d 个其他实体~";

        public String getSweepNoticeComplete() {
            return sweepNoticeComplete;
        }

        public String getSweepNotice() {
            return sweepNotice;
        }

        public int getSweepNotify() {
            return sweepNotify;
        }

        public int getSweepPeriod() {
            return sweepPeriod;
        }

        public int getSweepDiscount() {
            return sweepDiscount;
        }
    }

    public static class ItemEntitiesClean {
        @Expose
        private boolean isItemEntityCleanupEnable = true;
        @Expose
        private boolean itemEntitiesMatchMode = true;
        @Expose
        private Set<String> itemEntitiesWhitelist = Sets.newHashSet("minecraft:diamond", "minecraft:emerald");
        @Expose
        private Set<String> itemEntitiesBlacklist = Sets.newHashSet();

        public boolean isItemEntityCleanupEnable() {
            return isItemEntityCleanupEnable;
        }

        public void setItemEntityCleanupEnable(boolean itemEntityCleanupEnable) {
            isItemEntityCleanupEnable = itemEntityCleanupEnable;
        }


        public boolean isItemEntitiesMatchMode() {
            return itemEntitiesMatchMode;
        }

        public void setItemEntitiesMatchMode(boolean itemEntitiesMatchMode) {
            this.itemEntitiesMatchMode = itemEntitiesMatchMode;
        }

        public Set<String> getItemEntitiesWhitelist() {
            return itemEntitiesWhitelist;
        }

        public void setItemEntitiesWhitelist(Set<String> itemEntitiesWhitelist) {
            this.itemEntitiesWhitelist = itemEntitiesWhitelist;
        }

        public Set<String> getItemEntitiesBlacklist() {
            return itemEntitiesBlacklist;
        }

        public void setItemEntitiesBlacklist(Set<String> itemEntitiesBlacklist) {
            this.itemEntitiesBlacklist = itemEntitiesBlacklist;
        }

        public void addItemEntitiesWhitelist(String name){
            this.itemEntitiesWhitelist.add(name);
        }

        public void delItemEntitiesWhitelist(String name){
            this.itemEntitiesWhitelist.remove(name);
        }
    }
    public static class MobsClean{
        @Expose
        private boolean isAnimalEntitiesCleanupEnable = true;
        @Expose
        private boolean isMonsterEntitiesCleanupEnable = true;
        @Expose
        private boolean isMobEntityCleanupEnable = true;
        @Expose
        private boolean mobEntitiesMatchMode = true;
        @Expose
        private Set<String> mobEntitiesWhitelist = Sets.newHashSet("minecraft:cat", "minecraft:mule", "minecraft:wolf", "minecraft:horse",
                "minecraft:donkey", "minecraft:wither", "minecraft:guardian", "minecraft:villager", "minecraft:iron_golem", "minecraft:snow_golem",
                "minecraft:vindicator", "minecraft:ender_dragon", "minecraft:elder_guardian");
        @Expose
        private Set<String> mobEntitiesBlacklist = Sets.newHashSet();

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

        public Set<String> getMobEntitiesWhitelist() {
            return mobEntitiesWhitelist;
        }

        public void setMobEntitiesWhitelist(Set<String> mobEntitiesWhitelist) {
            this.mobEntitiesWhitelist = mobEntitiesWhitelist;
        }

        public Set<String> getMobEntitiesBlacklist() {
            return mobEntitiesBlacklist;
        }

        public void setMobEntitiesBlacklist(Set<String> mobEntitiesBlacklist) {
            this.mobEntitiesBlacklist = mobEntitiesBlacklist;
        }

        public void addMobEntitiesWhitelist(String name){
            this.mobEntitiesWhitelist.add(name);
        }

        public void delMobEntitiesWhitelist(String name){
            this.mobEntitiesWhitelist.remove(name);
        }
    }
    public static class OtherEntitiesClean{
        @Expose
        private boolean isExperienceOrbEntityCleanupEnable = true;
        @Expose
        private boolean isFallingBlocksEntityCleanupEnable = true;
        @Expose
        private boolean isArrowEntityCleanupEnable = true;
        @Expose
        private boolean isTridentEntityCleanupEnable = false;
        @Expose
        private boolean isDamagingProjectileEntityCleanupEnable = false;
        @Expose
        private boolean isShulkerBulletEntityCleanupEnable = true;
        @Expose
        private boolean isFireworkRocketEntityCleanupEnable = false;
        @Expose
        private boolean isItemFrameEntityCleanupEnable = false;
        @Expose
        private boolean isPaintingEntityCleanupEnable = false;
        @Expose
        private boolean isBoatEntityCleanupEnable = false;
        @Expose
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
    }



}

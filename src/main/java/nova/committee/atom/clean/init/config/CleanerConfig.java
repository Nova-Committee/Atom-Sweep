package nova.committee.atom.clean.init.config;

import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraftforge.common.ForgeConfigSpec;
import nova.committee.atom.clean.init.handler.CleanerHandler;

import java.util.*;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 21:38
 * Version: 1.0
 */
public class CleanerConfig{
    @SerializedName("items_clean")
    private ItemEntitiesClean itemsClean = new ItemEntitiesClean();
    @SerializedName("mobs_clean")
    private MobsClean mobsClean = new MobsClean();
    @SerializedName("others_clean")
    private OtherEntitiesClean othersClean = new OtherEntitiesClean();
    public String getConfigName() {
        return "atom_sweep";
    }

    public ItemEntitiesClean getItemsClean() {
        return itemsClean;
    }

    public MobsClean getMobsClean() {
        return mobsClean;
    }

    public OtherEntitiesClean getOthersClean() {
        return othersClean;
    }

    public static class ItemEntitiesClean{
        @Expose
        private boolean isItemEntityCleanupEnable = true;
        @Expose
        private int cleanupItemEntitiesIntervalSeconds = 300;
        @Expose
        private String cleanedupItemEntitiesMessage = "null";
        @Expose
        private int cleanupItemEntitiesCountdownSeconds = 30;
        @Expose
        private String cleanupItemEntitiesCountdownMessage = "null";
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

        public int getCleanupItemEntitiesIntervalSeconds() {
            return cleanupItemEntitiesIntervalSeconds;
        }

        public void setCleanupItemEntitiesIntervalSeconds(int cleanupItemEntitiesIntervalSeconds) {
            this.cleanupItemEntitiesIntervalSeconds = cleanupItemEntitiesIntervalSeconds;
        }

        public String getCleanedupItemEntitiesMessage() {
            return cleanedupItemEntitiesMessage;
        }

        public void setCleanedupItemEntitiesMessage(String cleanedupItemEntitiesMessage) {
            this.cleanedupItemEntitiesMessage = cleanedupItemEntitiesMessage;
        }

        public int getCleanupItemEntitiesCountdownSeconds() {
            return cleanupItemEntitiesCountdownSeconds;
        }

        public void setCleanupItemEntitiesCountdownSeconds(int cleanupItemEntitiesCountdownSeconds) {
            this.cleanupItemEntitiesCountdownSeconds = cleanupItemEntitiesCountdownSeconds;
        }

        public String getCleanupItemEntitiesCountdownMessage() {
            return cleanupItemEntitiesCountdownMessage;
        }

        public void setCleanupItemEntitiesCountdownMessage(String cleanupItemEntitiesCountdownMessage) {
            this.cleanupItemEntitiesCountdownMessage = cleanupItemEntitiesCountdownMessage;
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
        private boolean isMobEntityCleanupEnable = false;
        @Expose
        private boolean isAnimalEntitiesCleanupEnable = true;
        @Expose
        private boolean isMonsterEntitiesCleanupEnable = true;
        @Expose
        private int cleanupMobEntitiesIntervalSeconds = 360;
        @Expose
        private int cleanupMobEntitiesCountdownSeconds = 30;
        @Expose
        private String cleanedupMobEntitiesMessage = "null";
        @Expose
        private String cleanupMobEntitiesCountdownMessage = "null";

        private boolean mobEntitiesMatchMode = true;
        private Set<String> mobEntitiesWhitelist = Sets.newHashSet("minecraft:cat", "minecraft:mule", "minecraft:wolf", "minecraft:horse",
                "minecraft:donkey", "minecraft:wither", "minecraft:guardian", "minecraft:villager", "minecraft:iron_golem", "minecraft:snow_golem",
                "minecraft:vindicator", "minecraft:ender_dragon", "minecraft:elder_guardian");
        private Set<String> mobEntitiesBlacklist = Sets.newHashSet();

        public boolean isMobEntityCleanupEnable() {
            return isMobEntityCleanupEnable;
        }

        public void setMobEntityCleanupEnable(boolean mobEntityCleanupEnable) {
            isMobEntityCleanupEnable = mobEntityCleanupEnable;
        }

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

        public int getCleanupMobEntitiesIntervalSeconds() {
            return cleanupMobEntitiesIntervalSeconds;
        }

        public void setCleanupMobEntitiesIntervalSeconds(int cleanupMobEntitiesIntervalSeconds) {
            this.cleanupMobEntitiesIntervalSeconds = cleanupMobEntitiesIntervalSeconds;
        }

        public int getCleanupMobEntitiesCountdownSeconds() {
            return cleanupMobEntitiesCountdownSeconds;
        }

        public void setCleanupMobEntitiesCountdownSeconds(int cleanupMobEntitiesCountdownSeconds) {
            this.cleanupMobEntitiesCountdownSeconds = cleanupMobEntitiesCountdownSeconds;
        }

        public String getCleanedupMobEntitiesMessage() {
            return cleanedupMobEntitiesMessage;
        }

        public void setCleanedupMobEntitiesMessage(String cleanedupMobEntitiesMessage) {
            this.cleanedupMobEntitiesMessage = cleanedupMobEntitiesMessage;
        }

        public String getCleanupMobEntitiesCountdownMessage() {
            return cleanupMobEntitiesCountdownMessage;
        }

        public void setCleanupMobEntitiesCountdownMessage(String cleanupMobEntitiesCountdownMessage) {
            this.cleanupMobEntitiesCountdownMessage = cleanupMobEntitiesCountdownMessage;
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
        private int cleanupOtherEntitiesIntervalSeconds = 300;
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

        public int getCleanupOtherEntitiesIntervalSeconds() {
            return cleanupOtherEntitiesIntervalSeconds;
        }

        public void setCleanupOtherEntitiesIntervalSeconds(int cleanupOtherEntitiesIntervalSeconds) {
            this.cleanupOtherEntitiesIntervalSeconds = cleanupOtherEntitiesIntervalSeconds;
        }

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

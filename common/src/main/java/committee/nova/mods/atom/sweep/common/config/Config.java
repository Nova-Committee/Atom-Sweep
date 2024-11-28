package committee.nova.mods.atom.sweep.common.config;

import net.minecraft.Util;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 21:38
 * Version: 1.0
 */

public final class Config {

    public static Common COMMON;
    public static ForgeConfigSpec COMMON_SPEC;

    public static void initCommonConfig(Consumer<ForgeConfigSpec> registerConfig){
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
        registerConfig.accept(COMMON_SPEC);
    };

    public static class Common{
        public final ForgeConfigSpec.IntValue sweepPeriod;
        public final ForgeConfigSpec.IntValue sweepNotify;
        public final ForgeConfigSpec.IntValue sweepDiscount;
        public final ForgeConfigSpec.ConfigValue<? extends String> sweepNotice;
        public final ForgeConfigSpec.ConfigValue<? extends String> sweepNoticeComplete;

        public final ForgeConfigSpec.BooleanValue isItemEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue itemWhiteMode;
        public final ForgeConfigSpec.BooleanValue itemBlackMode;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> itemEntitiesWhitelist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> itemEntitiesBlacklist;

        public final ForgeConfigSpec.BooleanValue isExpOn;
        public final ForgeConfigSpec.BooleanValue isMobEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isMonsterEntitiesCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isAnimalEntitiesCleanupEnable;
        public final ForgeConfigSpec.BooleanValue mobWhiteMode;
        public final ForgeConfigSpec.BooleanValue mobBlackMode;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mobEntitiesWhitelist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mobEntitiesBlacklist;

        public final ForgeConfigSpec.BooleanValue isExperienceOrbEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isFallingBlocksEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isArrowEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isTridentEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isDamagingProjectileEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isShulkerBulletEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isFireworkRocketEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isItemFrameEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isPaintingEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isBoatEntityCleanupEnable;
        public final ForgeConfigSpec.BooleanValue isTNTEntityCleanupEnable;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Atom Sweep Config");
            builder.push("common");
            sweepPeriod = buildInt(builder, "sweepPeriod", 4, 0, 100000, "扫地周期（分钟）");
            sweepNotify = buildInt(builder, "sweepNotify", 20, 0, 100000, "提前通知时间（秒）");
            sweepDiscount = buildInt(builder, "sweepDiscount", 5, 0, 100000, "a");
            sweepNotice = buildString(builder, "sweepNotice", "<演变> 注意：还有 %d 秒就要去你家吃饭了~",
                    "通知提示");
            sweepNoticeComplete = buildString(builder, "sweepNoticeComplete", "<演变> 这次一共吃掉了 %d 个掉落物， %d 个生物 %d 个经验球和 %d 个其他实体~",
                    "清扫完通知提示");
            builder.pop();
            builder.push("item");
            isItemEntityCleanupEnable = buildBoolean(builder, "isItemEntityCleanupEnable", true, "物品实体清理功能");
            itemWhiteMode = buildBoolean(builder, "itemWhiteMode", true, "白名单模式");
            itemBlackMode = buildBoolean(builder, "itemBlackMode", true, "黑名单模式");
            itemEntitiesWhitelist = buildStrings(builder, "itemEntitiesWhitelist", Arrays.asList("minecraft:diamond", "minecraft:emerald"), "白名单(优先级高于黑名单),使用<modid:*>实现通配符功能");
            itemEntitiesBlacklist = buildStrings(builder, "itemEntitiesBlacklist", Collections.emptyList(), "黑名单,使用<modid:*>实现通配符功能");
            builder.pop();
            builder.push("entity");
            isMobEntityCleanupEnable = buildBoolean(builder, "isAnimalEntitiesCleanupEnable", true, "生物实体清理功能(最高优先级)");
            isExpOn = buildBoolean(builder, "isExpOn", false, "生物清理是否掉落经验");
            isAnimalEntitiesCleanupEnable = buildBoolean(builder, "isMonsterEntitiesCleanupEnable", true, "动物实体清理功能(次级)");
            isMonsterEntitiesCleanupEnable = buildBoolean(builder, "isMobEntityCleanupEnable", true, "怪物实体清理功能(次级)");
            mobWhiteMode = buildBoolean(builder, "mobWhiteMode", true, "白名单模式");
            mobBlackMode = buildBoolean(builder, "mobBlackMode", true, "黑名单模式");
            mobEntitiesWhitelist = buildStrings(builder, "mobEntitiesWhitelist", Arrays.asList("minecraft:chicken", "minecraft:cat", "minecraft:mule", "minecraft:wolf", "minecraft:horse", "minecraft:donkey",
                    "minecraft:wither", "minecraft:guardian", "minecraft:villager", "minecraft:iron_golem", "minecraft:snow_golem",
                    "minecraft:vindicator", "minecraft:ender_dragon", "minecraft:elder_guardian"), "生物白名单(优先级高于黑名单),使用<modid:*>实现通配符功能");
            mobEntitiesBlacklist = buildStrings(builder, "mobEntitiesBlacklist", Collections.emptyList(), "生物黑名单,使用<modid:*>实现通配符功能");
            builder.pop();
            builder.push("others");
            isExperienceOrbEntityCleanupEnable = buildBoolean(builder, "isExperienceOrbEntityCleanupEnable", true, "经验球实体清理功能");
            isFallingBlocksEntityCleanupEnable = buildBoolean(builder, "isFallingBlocksEntityCleanupEnable", true, "下落方块实体清理功能");
            isArrowEntityCleanupEnable = buildBoolean(builder, "isArrowEntityCleanupEnable", true, "箭头实体清理功能");
            isTridentEntityCleanupEnable = buildBoolean(builder, "isTridentEntityCleanupEnable", false, "三叉戟实体清理功能");
            isDamagingProjectileEntityCleanupEnable = buildBoolean(builder, "isDamagingProjectileEntityCleanupEnable", false, "投射物实体清理功能");
            isShulkerBulletEntityCleanupEnable = buildBoolean(builder, "isShulkerBulletEntityCleanupEnable", true, "子弹实体清理功能");
            isFireworkRocketEntityCleanupEnable = buildBoolean(builder, "isFireworkRocketEntityCleanupEnable", false, "烟花火箭实体清理功能");
            isItemFrameEntityCleanupEnable = buildBoolean(builder, "isItemFrameEntityCleanupEnable", false, "物品框实体清理功能");
            isPaintingEntityCleanupEnable = buildBoolean(builder, "isPaintingEntityCleanupEnable", false, "画实体清理功能");
            isBoatEntityCleanupEnable = buildBoolean(builder, "isBoatEntityCleanupEnable", false, "船实体清理功能");
            isTNTEntityCleanupEnable = buildBoolean(builder, "isTNTEntityCleanupEnable", true, "TNT实体清理功能");
            builder.pop();
        }
    
        public void saveAsync(){
            Util.ioPool().execute(()->COMMON_SPEC.save());
        }
    };

    private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, int defaultValue, int min, int max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ForgeConfigSpec.ConfigValue<String> buildString(ForgeConfigSpec.Builder builder, String name, String defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ForgeConfigSpec.ConfigValue<List<? extends String>> buildStrings(ForgeConfigSpec.Builder builder, String name, List<String> defaultValue, String comment) {
        return builder.comment(comment).translation(name).defineList(name, defaultValue, o -> o instanceof String);
    }

}

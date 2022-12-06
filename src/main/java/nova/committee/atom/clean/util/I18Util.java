package nova.committee.atom.clean.util;


import net.minecraft.util.text.*;
import nova.committee.atom.clean.Static;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 15:40
 * Version: 1.0
 */
public class I18Util {
    public static final int TITLE_COLOR = 0x404040;

    public static String getTranslationKey(String beforeModid, String afterModid) {
        beforeModid = beforeModid.endsWith(".") ? beforeModid : beforeModid + ".";
        afterModid = afterModid.startsWith(".") ? afterModid : "." + afterModid;
        return beforeModid + Static.MOD_ID + afterModid;
    }

    public static IFormattableTextComponent getColoredTextFromI18n(Color color, boolean bold,  boolean italic, String translationKey, Object... parameters) {
        return new TranslationTextComponent(translationKey, parameters)
                .setStyle(Style.EMPTY
                        .withColor(color)
                        .withBold(bold)
                        .withItalic(italic));
    }

    public static IFormattableTextComponent getWhiteTextFromI18n(boolean bold, boolean italic, String translationKey, Object... parameters) {
        return getColoredTextFromI18n(Color.fromLegacyFormat(TextFormatting.WHITE), bold,  italic, translationKey, parameters);
    }

    public static IFormattableTextComponent getGrayTextFromI18n(boolean bold,  boolean italic, String translationKey, Object... parameters) {
        return getColoredTextFromI18n(Color.fromLegacyFormat(TextFormatting.GRAY), bold,  italic, translationKey, parameters);
    }

    public static IFormattableTextComponent getContainerNameTextFromI18n(boolean bold,  boolean italic, String translationKey, Object... parameters) {
        return getColoredTextFromI18n(Color.parseColor(String.valueOf(TITLE_COLOR)), bold, italic, translationKey, parameters);
    }

    public static IFormattableTextComponent getGreenTextFromI18n(boolean bold , boolean italic, String translationKey, Object... parameters) {
        return getColoredTextFromI18n(Color.fromLegacyFormat(TextFormatting.GREEN), bold, italic, translationKey, parameters);
    }

    public static IFormattableTextComponent getRedTextFromI18n(boolean bold, boolean italic, String translationKey, Object... parameters) {
        return getColoredTextFromI18n(Color.fromLegacyFormat(TextFormatting.RED), bold, italic, translationKey, parameters);
    }

    public static IFormattableTextComponent getYellowTextFromI18n(boolean bold, boolean italic, String translationKey, Object... parameters) {
        return getColoredTextFromI18n(Color.fromLegacyFormat(TextFormatting.YELLOW), bold, italic, translationKey, parameters);
    }

    public static IFormattableTextComponent getColoredTextFromString(Color color, boolean bold, boolean underline, boolean italic, String text) {
        return new StringTextComponent(text)
                .setStyle(Style.EMPTY
                        .withColor(color)
                        .withBold(bold).withUnderlined(underline)
                        .withItalic(italic));
    }

    public static IFormattableTextComponent getGreenTextFromString(boolean bold, boolean underline, boolean italic, String text) {
        return getColoredTextFromString(Color.fromLegacyFormat(TextFormatting.GREEN), bold, underline, italic, text);
    }

    public static IFormattableTextComponent getYellowTextFromString(boolean bold, boolean underline, boolean italic, String text) {
        return getColoredTextFromString(Color.fromLegacyFormat(TextFormatting.YELLOW), bold, underline, italic, text);
    }

    public static IFormattableTextComponent getWhiteTextFromString(boolean bold, boolean underline, boolean italic, String text) {
        return getColoredTextFromString(Color.fromLegacyFormat(TextFormatting.WHITE), bold, underline, italic, text);
    }


}

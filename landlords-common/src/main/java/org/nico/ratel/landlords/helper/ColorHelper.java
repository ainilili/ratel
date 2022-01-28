package org.nico.ratel.landlords.helper;

import org.nico.ratel.landlords.enums.PokerType;
import org.nico.ratel.landlords.utils.StringUtils;

/**
 * add color to print text
 *
 * @author Goody
 * @version 1.0, 2022/1/28
 */
public final class ColorHelper {
    /** multi color format separator */
    private static final String COLOR_FORMAT_SEPARATOR = ";";
    /** the last \033[0m means back to normal color */
    private static final String COLOR_FORMAT = "\033[%sm%s\033[0m";

    /** RED text */
    public static final int COLOR_RED_TEXT = 91;
    /** black text */
    public static final int COLOR_BLACK_TEXT = 30;
    /** white text */
    public static final int COLOR_WHITE_TEXT = 97;
    /** white background */
    public static final int COLOR_WHITE_BACKGROUND = 7;
    /** black background */
    public static final int COLOR_BLACK_BACKGROUND = 40;

    private ColorHelper() {
    }

    /**
     * color the poker input
     *
     * @param pokerType pokerType
     * @param message   poker message
     * @return colorful message
     */
    public static String color(PokerType pokerType, String message) {
        switch (pokerType) {
            case DIAMOND:
            case HEART:
                return color(message, COLOR_RED_TEXT);
            case CLUB:
            case SPADE:
                return color(message, COLOR_WHITE_TEXT);
            case BLANK:
            default:
                return message;
        }
    }

    /**
     * <p>color the input message</p>
     *
     * <p>all color can be fount in {@link ColorHelperTest}</p>
     *
     * @param message input message
     * @param color   color number
     * @return colorful message
     */
    public static String color(String message, int color) {
        return String.format(COLOR_FORMAT, color, message);
    }

    /**
     * multi color the input message
     *
     * @param message input message
     * @param colors  color number array
     * @return colorful message
     */
    public static String color(String message, int... colors) {
        final String color = StringUtils.join(colors, COLOR_FORMAT_SEPARATOR);
        return String.format(COLOR_FORMAT, color, message);
    }
}

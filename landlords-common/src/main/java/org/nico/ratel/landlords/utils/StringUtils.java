package org.nico.ratel.landlords.utils;

import java.util.StringJoiner;

/**
 * utils for String
 *
 * @author Goody
 * @version 1.0, 2022/1/28
 */
public class StringUtils {

    public static final String EMPTY = "";

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     * </p>
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([1, 2, 3], ';')        = "1;2;3"
     * StringUtils.join([1, 2, 3], null)       = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(int[] array, String separator) {
        if (null == array) {
            return null;
        }
        if (null == separator) {
            separator = EMPTY;
        }
        final StringJoiner joiner = new StringJoiner(separator);
        for (int j : array) {
            joiner.add(String.valueOf(j));
        }
        return joiner.toString();
    }
}

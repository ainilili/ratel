package org.nico.ratel.landlords.helper;

import org.junit.Ignore;
import org.junit.Test;

/**
 * {@link ColorHelper} test
 *
 * @author Goody
 * @version 1.0, 2022/1/28
 */
public class ColorHelperTest {

    @Ignore
    @Test
    public void color_all() {
        final int size = 10;
        for (int i = 0; i< 100; i++) {
            if (i % size == 0) {
                System.out.println();
            }
            System.out.print(ColorHelper.color(String.format("number %3d color \t", i), i));
        }
    }

    @Ignore
    @Test
    public void color_mutil() {
        System.out.println(ColorHelper.color("color", 7, 30));
        System.out.println(ColorHelper.color("color", 91));
        System.out.println(ColorHelper.color("color", 40));
    }
}
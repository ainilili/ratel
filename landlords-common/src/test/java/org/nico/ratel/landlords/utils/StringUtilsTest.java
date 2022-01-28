package org.nico.ratel.landlords.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link StringUtils} test
 *
 * @author Goody
 * @version 1.0, 2022/1/28
 */
public class StringUtilsTest {

    @Test
    public void join() {
        Assert.assertNull(StringUtils.join(null, ","));
        Assert.assertEquals("", StringUtils.join(new int[]{}, ","));
        Assert.assertEquals("1,2,3", StringUtils.join(new int[]{1, 2, 3}, ","));
        Assert.assertEquals("123", StringUtils.join(new int[]{1, 2, 3}, null));
        Assert.assertEquals("123", StringUtils.join(new int[]{1, 2, 3}, StringUtils.EMPTY));
    }
}

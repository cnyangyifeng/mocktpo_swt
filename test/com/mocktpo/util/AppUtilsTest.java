package com.mocktpo.util;

import org.junit.Assert;
import org.junit.Test;

public class AppUtilsTest {

    @Test
    public void testIsMac() {
        Assert.assertEquals(true, AppUtils.isMac());
    }
}

package com.mocktpo.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.junit.Test;

public class ColorUtilsTest {

    @Test
    public void testFromHex() {
        Color c = ColorUtils.fromHex(Display.getDefault(), "#333333");
        System.out.println("RGB: (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
    }
}

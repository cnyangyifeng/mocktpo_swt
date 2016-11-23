package com.mocktpo.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import java.util.concurrent.atomic.AtomicReference;

public class FontUtils {

    private FontUtils() {
    }

    public static Font getFont(final Display d, final int height) {
        return getFont(d, null, height, SWT.NORMAL);
    }

    public static Font getFont(final Display d, final int height, final int style) {
        return getFont(d, null, height, style);
    }

    public static Font getFont(final Display d, final String name, final int height, final int style) {
        final AtomicReference<FontData> arf = new AtomicReference<FontData>();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                arf.set(d.getSystemFont().getFontData()[0]);
            }
        });
        FontData fd = arf.get();
        if (null != name) {
            fd.setName(name);
        }
        if (0 != height) {
            fd.setHeight(pixelsToPoints(d, height));
        }
        if (SWT.NORMAL != style) {
            fd.setStyle(style);
        }
        return new Font(d, fd);
    }

    private static int pixelsToPoints(final Display d, final int pixels) {
        final AtomicReference<Integer> ari = new AtomicReference<Integer>();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                /*
                 * Windows DPI: 96 (100%), 120 (125%), 144 (150%), 192 (200%)
                 * Mac OS X DPI: 72
                 */
                ari.set(d.getDPI().y);
            }
        });
        return Math.round(pixels * 72 / ari.get());
    }
}

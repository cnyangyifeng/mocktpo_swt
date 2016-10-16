package com.mocktpo.util;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;

public class SWTFontUtils {

    private static final ConcurrentMap<Device, Font> MONOSPACED_FONTS = new ConcurrentHashMap<Device, Font>();

    private SWTFontUtils() {
    }

    public static Font getMonospacedFont() {
        Display d = Display.getCurrent();
        if (null == d) {
            throw new IllegalStateException();
        }
        return getMonospacedFont(d);
    }

    public static Font getMonospacedFont(final Display d) {
        Font cachedFont = MONOSPACED_FONTS.get(d);
        if (cachedFont != null)
            return cachedFont;
        String os = System.getProperty("os.name");
        String ws = SWT.getPlatform();
        os = StringUtils.deleteWhitespace(os).toLowerCase(Locale.US);
        ws = StringUtils.deleteWhitespace(ws).toLowerCase(Locale.US);
        String[] names = { os + "_" + ws, os, "" };
        String[] fontDataTxts = null;
        for (String name : names) {
            if (name.equals("macosx")) {
                fontDataTxts = new String[] { "Monaco|normal|11", "Courier|normal|12", "Courier New|normal|12" };
                break;
            } else if (name.equals("windows98")) {
                fontDataTxts = new String[] { "Courier New|normal|10", "Courier|normal|10", "Lucida Console|normal|9" };
                break;
            } else if (name.equals("windowsnt")) {
                fontDataTxts = new String[] { "Courier New|normal|10", "Courier|normal|10", "Lucida Console|normal|9" };
                break;
            } else if (name.equals("windows2000")) {
                fontDataTxts = new String[] { "Courier New|normal|10", "Courier|normal|10", "Lucida Console|normal|9" };
                break;
            } else if (name.equals("windowsxp")) {
                fontDataTxts = new String[] { "Courier New|normal|10", "Courier|normal|10", "Lucida Console|normal|9" };
                break;
            } else if (name.equals("windowsvista")) {
                fontDataTxts = new String[] { "Consolas|normal|10", "Courier New|normal|10" };
                break;
            } else if (name.equals("windows7")) {
                fontDataTxts = new String[] { "Consolas|normal|10", "Courier New|normal|10" };
                break;
            } else if (name.equals("windows8")) {
                fontDataTxts = new String[] { "Consolas|normal|10", "Courier New|normal|10" };
                break;
            } else if (name.equals("")) {
                fontDataTxts = new String[] { "Courier New|normal|10", "Courier|normal|10" };
                break;
            }
        }
        if (fontDataTxts == null) {
            throw new AssertionError();
        }
        FontData[] fontDatas = new FontData[fontDataTxts.length];
        for (int i = 0; i < fontDatas.length; i++) {
            String txt = fontDataTxts[i];
            int bar2 = txt.lastIndexOf('|');
            Assert.assertTrue(bar2 != -1);
            int bar1 = txt.lastIndexOf('|', bar2 - 1);
            Assert.assertTrue(bar1 != -1);
            String name = txt.substring(0, bar1);
            Assert.assertTrue(name.length() > 0);
            String[] styles = txt.substring(bar1 + 1, bar2).split(",");
            int style = 0;
            for (String s : styles) {
                if (s.equals("normal")) {
                    style |= SWT.NORMAL;
                } else if (s.equals("bold")) {
                    style |= SWT.BOLD;
                } else if (s.equals("italic")) {
                    style |= SWT.ITALIC;
                } else {
                    throw new RuntimeException("Invalid style: " + s);
                }
            }
            int height = Integer.parseInt(txt.substring(bar2 + 1));
            fontDatas[i] = new FontData(name, height, style);
        }
        final Font font = new Font(d, fontDatas);
        d.disposeExec(new Runnable() {
            @Override
            public void run() {
                synchronized (MONOSPACED_FONTS) {
                    MONOSPACED_FONTS.remove(d);
                    font.dispose();
                }
            }
        });
        MONOSPACED_FONTS.put(d, font);
        return font;
    }
}

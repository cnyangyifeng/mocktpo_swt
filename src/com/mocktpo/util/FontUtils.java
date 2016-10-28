package com.mocktpo.util;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;

public class FontUtils {

    private static final ConcurrentMap<Device, Font> MONOSPACED_FONTS = new ConcurrentHashMap<Device, Font>();

    private FontUtils() {
    }

    public static Font getFont(final Display d, final int height) {
        return getFont(d, null, height, SWT.NORMAL);
    }

    public static Font getFont(final Display d, final int height, final int style) {
        return getFont(d, null, height, style);
    }

    public static Font getFont(final Display d, final String name, final int height, final int style) {
        final AtomicReference<FontData> afd = new AtomicReference<FontData>();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                afd.set(d.getSystemFont().getFontData()[0]);
            }
        });
        FontData fd = afd.get();
        if (null != name) {
            fd.setName(name);
        }
        if (0 != height) {
            fd.setHeight(height);
        }
        if (SWT.NORMAL != style) {
            fd.setStyle(style);
        }
        return new Font(d, fd);
    }

    public static Font getMonospacedFont(final Display d) {
        Font cache = MONOSPACED_FONTS.get(d);
        if (null != cache) {
            return cache;
        }
        String os = System.getProperty("os.name");
        String ws = SWT.getPlatform();
        os = StringUtils.deleteWhitespace(os).toLowerCase(Locale.US);
        ws = StringUtils.deleteWhitespace(ws).toLowerCase(Locale.US);
        String[] names = { os + "_" + ws, os, "" };
        String[] fontDataTxts = null;
        for (String name : names) {
            if (name.equals("macosx")) {
                fontDataTxts = new String[] { "Monaco|normal|12", "Courier|normal|12", "Courier New|normal|12" };
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
        if (null == fontDataTxts) {
            throw new AssertionError();
        }
        FontData[] fds = new FontData[fontDataTxts.length];
        for (int i = 0; i < fds.length; i++) {
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
            fds[i] = new FontData(name, height, style);
        }
        final Font f = new Font(d, fds);
        d.disposeExec(new Runnable() {
            @Override
            public void run() {
                synchronized (MONOSPACED_FONTS) {
                    MONOSPACED_FONTS.remove(d);
                    f.dispose();
                }
            }
        });
        MONOSPACED_FONTS.put(d, f);
        return f;
    }
}

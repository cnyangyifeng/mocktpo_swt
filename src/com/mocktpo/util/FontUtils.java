package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicReference;

public class FontUtils {

    private static final String DEFAULT_FONT_NAME = "Roboto";

    private FontUtils() {
    }

    public static Font getFont(final Display d, final int height) {
        return getFont(d, DEFAULT_FONT_NAME, height, SWT.NORMAL);
    }

    public static Font getFont(final Display d, final int height, final int style) {
        return getFont(d, DEFAULT_FONT_NAME, height, style);
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
        if (name != null) {
            fd.setName(name);
        }
        if (height != 0) {
            fd.setHeight(pixelsToPoints(d, height));
        }
        if (style != SWT.NORMAL) {
            fd.setStyle(style);
        }
        return new Font(d, fd);
    }

    public static void loadExternalFonts(final Display d) {
        d.asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Black.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-BlackItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Bold.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-BoldItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Italic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Light.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-LightItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Medium.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-MediumItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Regular.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-Thin.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "Roboto-ThinItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "RobotoCondensed-Bold.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "RobotoCondensed-BoldItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "RobotoCondensed-Light.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "RobotoCondensed-LightItalic.ttf", "utf-8"));
                    d.loadFont(URLDecoder.decode(RC.FONTS_DIR + "roboto" + MT.STRING_SLASH + "RobotoCondensed-Regular.ttf", "utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

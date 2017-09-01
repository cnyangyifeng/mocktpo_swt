package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import java.io.File;
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
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Black.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-BlackItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Bold.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-BoldItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Italic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Light.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-LightItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Medium.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-MediumItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Regular.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-Thin.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "Roboto-ThinItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "RobotoCondensed-Bold.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "RobotoCondensed-BoldItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "RobotoCondensed-Italic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "RobotoCondensed-Light.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "RobotoCondensed-LightItalic.ttf"));
                    d.loadFont(getfullFontFileName("roboto", "RobotoCondensed-Regular.ttf"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String getfullFontFileName(String fontDir, String fontName) {
        try {
            File f = new File(FontUtils.class.getResource(URLDecoder.decode(RC.FONTS_DIR + fontDir + MT.STRING_SLASH + fontName, "utf-8")).toURI());
            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
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

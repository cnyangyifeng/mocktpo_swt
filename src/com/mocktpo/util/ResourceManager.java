package com.mocktpo.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.util.constants.MT;

public class ResourceManager {

    private static final int INITIAL_CACHE_SIZE = 64;

    private static final ConcurrentMap<Integer, Resource> caches = new ConcurrentHashMap<Integer, Resource>(INITIAL_CACHE_SIZE);

    public static void alloc(Display d) {

        /* Colors */

        caches.putIfAbsent(MT.COLOR_BLUE, new Color(d, 14, 116, 219)); // #0e74db
        caches.putIfAbsent(MT.COLOR_BLUE_PURPLE, new Color(d, 60, 77, 130)); // #3c4d82
        caches.putIfAbsent(MT.COLOR_DARK_BLUE, new Color(d, 47, 82, 140)); // #2f528c
        caches.putIfAbsent(MT.COLOR_DARK_GRAY, new Color(d, 65, 74, 78)); // #414a4e
        caches.putIfAbsent(MT.COLOR_DARK_RED, new Color(d, 135, 33, 52)); // #872134
        caches.putIfAbsent(MT.COLOR_DARK_TEXT_GRAY, new Color(d, 51, 51, 51)); // #333333
        caches.putIfAbsent(MT.COLOR_DUST_RED, new Color(d, 234, 203, 192)); // #eacbc0
        caches.putIfAbsent(MT.COLOR_GRAY, new Color(d, 220, 220, 220)); // #dcdcdc
        caches.putIfAbsent(MT.COLOR_GREEN, new Color(d, 92, 184, 92)); // #5cb85c
        caches.putIfAbsent(MT.COLOR_LIGHT_GRAY, new Color(d, 239, 239, 239)); // #efefef
        caches.putIfAbsent(MT.COLOR_LIGHT_TEXT_GRAY, new Color(d, 203, 213, 221)); // #cbd5dd
        caches.putIfAbsent(MT.COLOR_LIGHT_YELLOW, new Color(d, 242, 232, 200)); // #f2e8c8
        caches.putIfAbsent(MT.COLOR_LIGHTER_GRAY, new Color(d, 244, 244, 244)); // #f4f4f4
        caches.putIfAbsent(MT.COLOR_RED, new Color(d, 217, 83, 79)); // #d9534f
        caches.putIfAbsent(MT.COLOR_WHITE, new Color(d, 255, 255, 255)); // #ffffff

        /* Cursors */

        caches.putIfAbsent(MT.CURSOR_HAND, new Cursor(d, SWT.CURSOR_HAND));

        /* Fonts */

        caches.putIfAbsent(MT.FONT_SERIF_TITLE, FontUtils.getFont(d, "georgia", 20, SWT.BOLD));
        caches.putIfAbsent(MT.FONT_SMALL, FontUtils.getFont(d, 10));
        caches.putIfAbsent(MT.FONT_SUBTITLE, FontUtils.getFont(d, 16));
        caches.putIfAbsent(MT.FONT_TITLE, FontUtils.getFont(d, 20));

        /* Images */

        caches.putIfAbsent(MT.IMAGE_APP_ICON, ImageUtils.load(d, "icon"));
        caches.putIfAbsent(MT.IMAGE_ARROW_RIGHT, ImageUtils.load(d, "arrow_right"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE, ImageUtils.load(d, "continue"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_HOVER, ImageUtils.load(d, "continue_hover"));
        caches.putIfAbsent(MT.IMAGE_ETS_TOEFL, ImageUtils.load(d, "ets_toefl"));
        caches.putIfAbsent(MT.IMAGE_LOGO, ImageUtils.load(d, "logo"));
        caches.putIfAbsent(MT.IMAGE_PAUSE_TEST, ImageUtils.load(d, "pause_test"));
        caches.putIfAbsent(MT.IMAGE_PAUSE_TEST_HOVER, ImageUtils.load(d, "pause_test_hover"));
        caches.putIfAbsent(MT.IMAGE_SETTINGS, ImageUtils.load(d, "settings"));
        caches.putIfAbsent(MT.IMAGE_SPLASH, ImageUtils.load(d, "splash"));

    }

    public static void dispose() {
        for (int key : caches.keySet()) {
            Resource r = caches.get(key);
            caches.remove(key);
            r.dispose();
        }
    }

    public static Color getColor(int key) {
        return (Color) caches.get(key);
    }

    public static Cursor getCursor(int key) {
        return (Cursor) caches.get(key);
    }

    public static Font getFont(int key) {
        return (Font) caches.get(key);
    }

    public static Image getImage(int key) {
        return (Image) caches.get(key);
    }
}

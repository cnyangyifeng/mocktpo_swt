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

    private static final ConcurrentMap<Integer, Resource> caches = new ConcurrentHashMap<>(INITIAL_CACHE_SIZE);

    public static void alloc(Display d) {

        /* Colors */

        caches.putIfAbsent(MT.COLOR_BLUE, new Color(d, 14, 116, 219)); // #0e74db
        caches.putIfAbsent(MT.COLOR_DARK_GRAY, new Color(d, 67, 76, 79)); // #434c4f
        caches.putIfAbsent(MT.COLOR_GRAY, new Color(d, 220, 220, 220));
        caches.putIfAbsent(MT.COLOR_GREEN, new Color(d, 92, 184, 92));
        caches.putIfAbsent(MT.COLOR_LIGHT_GRAY, new Color(d, 239, 239, 239));
        caches.putIfAbsent(MT.COLOR_LIGHTER_GRAY, new Color(d, 246, 246, 246));
        caches.putIfAbsent(MT.COLOR_RED, new Color(d, 217, 83, 79));
        caches.putIfAbsent(MT.COLOR_WHITE, new Color(d, 255, 255, 255));

        /* Cursors */

        caches.putIfAbsent(MT.CURSOR_HAND, new Cursor(d, SWT.CURSOR_HAND));

        /* Fonts */

        caches.putIfAbsent(MT.FONT_TITLE, FontUtils.getSystemFont(d, 20));

        /* Images */

        caches.putIfAbsent(MT.IMAGE_APP_ICON, ImageUtils.load(d, "icon"));
        caches.putIfAbsent(MT.IMAGE_ARROW_RIGHT, ImageUtils.load(d, "arrow_right"));
        caches.putIfAbsent(MT.IMAGE_LOGO, ImageUtils.load(d, "logo"));
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

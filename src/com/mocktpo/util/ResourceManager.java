package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ResourceManager {

    private static final int INITIAL_CACHE_SIZE = 64;

    private static final ConcurrentMap<Integer, Resource> caches = new ConcurrentHashMap<Integer, Resource>(INITIAL_CACHE_SIZE);

    public static void alloc(Display d) {

        /*
         * ==================================================
         * 
         * Colors
         * 
         * ==================================================
         */

        caches.putIfAbsent(MT.COLOR_BLACK, new Color(d, 0, 0, 0)); // #000000
        caches.putIfAbsent(MT.COLOR_BURGUNDY, new Color(d, 132, 0, 50)); // #840032
        caches.putIfAbsent(MT.COLOR_DARK_ORANGE, new Color(d, 255, 140, 0)); // #ff8c00
        caches.putIfAbsent(MT.COLOR_DARK_BLUE, new Color(d, 47, 82, 140)); // #2f528c
        caches.putIfAbsent(MT.COLOR_GAINSBORO, new Color(d, 229, 218, 218)); // #e5dada
        caches.putIfAbsent(MT.COLOR_GRANITE_GRAY, new Color(d, 96, 95, 94)); // #605f5e
        caches.putIfAbsent(MT.COLOR_GREEN, new Color(d, 0, 128, 0)); // #008000
        caches.putIfAbsent(MT.COLOR_INDIGO, new Color(d, 55, 50, 125)); // #37327d
        caches.putIfAbsent(MT.COLOR_KHAKI, new Color(d, 242, 232, 200)); // #f2e8c8
        caches.putIfAbsent(MT.COLOR_ORANGE, new Color(d, 255, 165, 0)); // #ffa500
        caches.putIfAbsent(MT.COLOR_ORANGE_RED, new Color(d, 255, 69, 0)); // #ff4500
        caches.putIfAbsent(MT.COLOR_OXFORD_BLUE, new Color(d, 0, 38, 66)); // #002642
        caches.putIfAbsent(MT.COLOR_SADDLE_BROWN, new Color(d, 139, 69, 19)); // #8b4513
        caches.putIfAbsent(MT.COLOR_WHITE, new Color(d, 255, 255, 255)); // #ffffff
        caches.putIfAbsent(MT.COLOR_WHITE_SMOKE, new Color(d, 245, 245, 245)); // #f5f5f5

        /* Others */

        caches.putIfAbsent(MT.COLOR_BORDER, new Color(d, 220, 220, 220)); // #dcdcdc
        caches.putIfAbsent(MT.COLOR_MAJOR_BACKGROUND, new Color(d, 239, 239, 239)); // #efefef

        /*
         * ==================================================
         * 
         * Cursors
         * 
         * ==================================================
         */

        caches.putIfAbsent(MT.CURSOR_ARROW, new Cursor(d, SWT.CURSOR_ARROW));
        caches.putIfAbsent(MT.CURSOR_HAND, new Cursor(d, SWT.CURSOR_HAND));

        /*
         * ==================================================
         * 
         * Fonts
         * 
         * ==================================================
         */

        /* 8px, 6pt, 50% */

        caches.putIfAbsent(MT.FONT_XX_SMALL, FontUtils.getFont(d, 8));

        /* 10px, 7.5pt(8pt), 62.5% */

        caches.putIfAbsent(MT.FONT_X_SMALL, FontUtils.getFont(d, 10));

        /* 12px, 9pt, 75% */

        caches.putIfAbsent(MT.FONT_SMALL, FontUtils.getFont(d, 12));
        caches.putIfAbsent(MT.FONT_SMALL_BOLD, FontUtils.getFont(d, 12, SWT.BOLD));

        /* 16px, 12pt, 100% */

        caches.putIfAbsent(MT.FONT_MEDIUM, FontUtils.getFont(d, 16));
        caches.putIfAbsent(MT.FONT_MEDIUM_BOLD, FontUtils.getFont(d, 16, SWT.BOLD));

        /* 20px, 15pt, 125% */

        caches.putIfAbsent(MT.FONT_LARGE, FontUtils.getFont(d, 20));
        caches.putIfAbsent(MT.FONT_LARGE_BOLD, FontUtils.getFont(d, 20, SWT.BOLD));

        /* 24px, 18pt, 150% */

        caches.putIfAbsent(MT.FONT_X_LARGE, FontUtils.getFont(d, 24));

        /* 32px, 24pt, 200% */

        caches.putIfAbsent(MT.FONT_XX_LARGE, FontUtils.getFont(d, 32));

        /* Others */

        caches.putIfAbsent(MT.FONT_ACTIVATION_CODE, FontUtils.getFont(d, "Courier New", 12, SWT.NORMAL));
        caches.putIfAbsent(MT.FONT_SERIF_HEADING, FontUtils.getFont(d, "Georgia", 20, SWT.BOLD));

        /*
         * ==================================================
         * 
         * Images
         * 
         * ==================================================
         */

        caches.putIfAbsent(MT.IMAGE_APP_ICON, ImageUtils.load(d, "icon"));
        caches.putIfAbsent(MT.IMAGE_ARROW_RIGHT, ImageUtils.load(d, "arrow_right"));
        caches.putIfAbsent(MT.IMAGE_BACK_OVAL, ImageUtils.load(d, "back_o"));
        caches.putIfAbsent(MT.IMAGE_BACK_OVAL_HOVER, ImageUtils.load(d, "back_o_h"));
        caches.putIfAbsent(MT.IMAGE_BACK_OVAL_DISABLED, ImageUtils.load(d, "back_o_d"));
        caches.putIfAbsent(MT.IMAGE_BULLET, ImageUtils.load(d, "bullet"));
        caches.putIfAbsent(MT.IMAGE_CHECKED, ImageUtils.load(d, "checked"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE, ImageUtils.load(d, "continue"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_HOVER, ImageUtils.load(d, "continue_h"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_OVAL, ImageUtils.load(d, "continue_o"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_OVAL_HOVER, ImageUtils.load(d, "continue_o_h"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_OVAL_DISABLED, ImageUtils.load(d, "continue_o_d"));
        caches.putIfAbsent(MT.IMAGE_ETS_TOEFL, ImageUtils.load(d, "ets_toefl"));
        caches.putIfAbsent(MT.IMAGE_GO_TO_QUESTION, ImageUtils.load(d, "go_to_question"));
        caches.putIfAbsent(MT.IMAGE_GO_TO_QUESTION_HOVER, ImageUtils.load(d, "go_to_question_h"));
        caches.putIfAbsent(MT.IMAGE_HELP_OVAL, ImageUtils.load(d, "help_o"));
        caches.putIfAbsent(MT.IMAGE_HELP_OVAL_HOVER, ImageUtils.load(d, "help_o_h"));
        caches.putIfAbsent(MT.IMAGE_HELP_OVAL_DISABLED, ImageUtils.load(d, "help_o_d"));
        caches.putIfAbsent(MT.IMAGE_HIDE_TIME, ImageUtils.load(d, "hide_time"));
        caches.putIfAbsent(MT.IMAGE_HIDE_TIME_HOVER, ImageUtils.load(d, "hide_time_h"));
        caches.putIfAbsent(MT.IMAGE_HIDE_TIME_DISABLED, ImageUtils.load(d, "hide_time_d"));
        caches.putIfAbsent(MT.IMAGE_LOGO, ImageUtils.load(d, "logo"));
        caches.putIfAbsent(MT.IMAGE_NEXT_OVAL, ImageUtils.load(d, "next_o"));
        caches.putIfAbsent(MT.IMAGE_NEXT_OVAL_HOVER, ImageUtils.load(d, "next_o_h"));
        caches.putIfAbsent(MT.IMAGE_NEXT_OVAL_DISABLED, ImageUtils.load(d, "next_o_d"));
        caches.putIfAbsent(MT.IMAGE_OK_OVAL, ImageUtils.load(d, "ok_o"));
        caches.putIfAbsent(MT.IMAGE_OK_OVAL_HOVER, ImageUtils.load(d, "ok_o_h"));
        caches.putIfAbsent(MT.IMAGE_OK_OVAL_DISABLED, ImageUtils.load(d, "ok_o_d"));
        caches.putIfAbsent(MT.IMAGE_PAUSE_TEST, ImageUtils.load(d, "pause_test"));
        caches.putIfAbsent(MT.IMAGE_PAUSE_TEST_HOVER, ImageUtils.load(d, "pause_test_h"));
        caches.putIfAbsent(MT.IMAGE_RETURN, ImageUtils.load(d, "return"));
        caches.putIfAbsent(MT.IMAGE_RETURN_HOVER, ImageUtils.load(d, "return_h"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_OVAL, ImageUtils.load(d, "review_o"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_OVAL_HOVER, ImageUtils.load(d, "review_o_h"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_OVAL_DISABLED, ImageUtils.load(d, "review_o_d"));
        caches.putIfAbsent(MT.IMAGE_SHOW_TIME, ImageUtils.load(d, "show_time"));
        caches.putIfAbsent(MT.IMAGE_SHOW_TIME_HOVER, ImageUtils.load(d, "show_time_h"));
        caches.putIfAbsent(MT.IMAGE_SHOW_TIME_DISABLED, ImageUtils.load(d, "show_time_d"));
        caches.putIfAbsent(MT.IMAGE_SPLASH, ImageUtils.load(d, "splash"));
        caches.putIfAbsent(MT.IMAGE_VOLUME_OVAL, ImageUtils.load(d, "volume_o"));
        caches.putIfAbsent(MT.IMAGE_VOLUME_OVAL_HOVER, ImageUtils.load(d, "volume_o_h"));
        caches.putIfAbsent(MT.IMAGE_VIEW_QUESTION, ImageUtils.load(d, "view_question"));
        caches.putIfAbsent(MT.IMAGE_VIEW_QUESTION_HOVER, ImageUtils.load(d, "view_question_h"));
        caches.putIfAbsent(MT.IMAGE_VIEW_TEXT, ImageUtils.load(d, "view_text"));
        caches.putIfAbsent(MT.IMAGE_VIEW_TEXT_HOVER, ImageUtils.load(d, "view_text_h"));
        caches.putIfAbsent(MT.IMAGE_UNCHECKED, ImageUtils.load(d, "unchecked"));
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

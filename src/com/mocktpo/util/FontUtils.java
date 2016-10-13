package com.mocktpo.util;

import org.eclipse.swt.widgets.Display;

import com.mocktpo.util.constants.ResourceConstants;

public class FontUtils {

    public static void loadFonts(Display d) {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    try {
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Black.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-BlackItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Bold.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-BoldItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Italic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Light.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-LightItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Medium.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-MediumItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Regular.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-Thin.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "Roboto-ThinItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "RobotoCondensed-Bold.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "RobotoCondensed-BoldItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "RobotoCondensed-Italic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "RobotoCondensed-Light.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "RobotoCondensed-LightItalic.ttf");
                        d.loadFont(ResourceConstants.FONTS_DIR + "RobotoCondensed-Regular.ttf");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

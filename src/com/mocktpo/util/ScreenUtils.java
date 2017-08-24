package com.mocktpo.util;

import com.mocktpo.util.constants.LC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

/********************************************************************************
 *
 * 2560 : 1920 : 1280 = 2x : 1.5x : 1x
 *
 * Use Large (2x) images, if screen width approximately equals with 2560px;
 *
 * e.g. 2560x1600 (8:5), or above
 *
 * Use Medium (1.5x) images, if screen width approximately equals with 1920px;
 *
 * e.g. 1920x1200 (8:5), 1920x1080 (16:9), 1680x1050 (8:5), 1600x900 (16:9)
 *
 * Use Small (1x) images, if screen width approximately equals with 1280px;
 *
 * e.g. 1440x900 (8:5), 1366x768 (16:9), 1280x1024 (5:4), 1280x800 (8:5)
 *
 *******************************************************************************/

public class ScreenUtils {

    private static Point screenSize;
    private static Point clientSize;
    private static Point viewPort;

    private ScreenUtils() {
    }

    public static int getScreenType(Display d) {
        int w = getScreenSize(d).x;
        if (w > LC.REF_MEDIUM_SCREEN_WIDTH) {
            return LC.SCREEN_LARGE;
        } else if (w <= LC.REF_MEDIUM_SCREEN_WIDTH && w > LC.REF_SMALL_SCREEN_WIDTH) {
            return LC.SCREEN_MEDIUM;
        } else {
            return LC.SCREEN_SMALL;
        }
    }

    public static Point getScreenSize(Display d) {
        if (screenSize == null) {
            Rectangle bounds = d.getPrimaryMonitor().getBounds();
            screenSize = new Point(bounds.width, bounds.height);
        }
        return screenSize;
    }

    public static Point getViewPort(Display d) {
        if (viewPort == null) {
            float proportion = 0.9f;
            int w = (int) (d.getPrimaryMonitor().getClientArea().width * proportion);
            int h = (int) (d.getPrimaryMonitor().getClientArea().height * proportion);
            viewPort = new Point(w, h);
        }
        return viewPort;
    }

    public static Point getClientSize(Display d) {
        if (clientSize == null) {
            Rectangle bounds = d.getPrimaryMonitor().getClientArea();
            clientSize = new Point(bounds.width, bounds.height);
        }
        return clientSize;
    }

    public static int getClientWidth(Display d) {
        return getClientSize(d).x;
    }
}

package com.mocktpo.util;

import com.mocktpo.util.constants.LC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

public class WindowUtils {

    private WindowUtils() {
    }

    public static void center(Shell s) {
        Rectangle bounds = s.getDisplay().getPrimaryMonitor().getClientArea();
        Point point = s.getSize();
        int x = (bounds.width - point.x) / 2;
        int y = (bounds.height - point.y) / 2;
        s.setBounds(x, y, point.x, point.y);
    }

    public static void disableFullscreen(Shell s) {
        /*
         * if (PlatformUtils.isMac()) { NSWindow nswindow = s.view.window();
         * nswindow.setCollectionBehavior(0);
         * nswindow.setShowsResizeIndicator(false); }
         * 
         */
        s.setFullScreen(false);
    }

    public static void setDefaultWindowSize(Shell s) {
        Point p = ScreenUtils.getViewPort(s.getDisplay());
        int reserved = 40;
        s.setSize(p.x + reserved, p.y);
    }

    public static void setMinimumWindowSize(Shell s) {
        Point p = ScreenUtils.getViewPort(s.getDisplay());
        int reserved = 40;
        s.setMinimumSize(p.x + reserved, p.y);
    }

    public static void setLeftDialogBounds(Shell s) {
        Point clientSize = ScreenUtils.getClientSize(s.getDisplay());
        int x = (clientSize.x / 2 - LC.DIALOG_WIDTH_HINT) / 2;
        int y = (clientSize.y - LC.DIALOG_HEIGHT_HINT) / 2;
        s.setBounds(x, y, LC.DIALOG_WIDTH_HINT, LC.DIALOG_HEIGHT_HINT);
    }

    public static void setCenterDialogBounds(Shell s) {
        Point clientSize = ScreenUtils.getClientSize(s.getDisplay());
        int x = (clientSize.x - LC.DIALOG_WIDTH_HINT) / 2;
        int y = (clientSize.y - LC.DIALOG_HEIGHT_HINT) / 2;
        s.setBounds(x, y, LC.DIALOG_WIDTH_HINT, LC.DIALOG_HEIGHT_HINT);
    }
}

package com.mocktpo.util;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

public class WindowUtils {

    public static void center(Shell s) {
        Rectangle bounds = s.getDisplay().getPrimaryMonitor().getClientArea();
        Point point = s.getSize();
        int x = (bounds.width - point.x) / 2;
        int y = (bounds.height - point.y) / 2;
        s.setBounds(x, y, point.x, point.y);
    }
}

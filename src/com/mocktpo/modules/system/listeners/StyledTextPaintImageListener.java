package com.mocktpo.modules.system.listeners;

import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Image;

public class StyledTextPaintImageListener implements PaintObjectListener {

    @Override
    public void paintObject(PaintObjectEvent e) {
        StyleRange style = e.style;
        if (style.data != null) {
            Image image = (Image) style.data;
            if (!image.isDisposed()) {
                int x = e.x;
                int y = e.y + e.ascent - style.metrics.ascent;
                e.gc.drawImage(image, x, y);
            }
        }
    }
}

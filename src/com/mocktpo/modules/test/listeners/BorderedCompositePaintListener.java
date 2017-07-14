package com.mocktpo.modules.test.listeners;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public class BorderedCompositePaintListener implements PaintListener {

    private int color;

    public BorderedCompositePaintListener(int color) {
        this.color = color;
    }

    @Override
    public void paintControl(PaintEvent e) {
        GC gc = e.gc;
        Rectangle c = ((Control) e.widget).getBounds();
        gc.setForeground(ResourceManager.getColor(this.color));
        gc.drawRectangle(0, 0, c.width - 1, c.height - 1);
    }
}

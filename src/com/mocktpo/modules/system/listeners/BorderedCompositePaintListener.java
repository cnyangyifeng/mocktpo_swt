package com.mocktpo.modules.system.listeners;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public class BorderedCompositePaintListener implements PaintListener {

    private String color;
    private int lineWidth;

    public BorderedCompositePaintListener(String color) {
        this.color = color;
        this.lineWidth = 1;
    }

    public BorderedCompositePaintListener(String color, int lineWidth) {
        this.color = color;
        this.lineWidth = lineWidth;
    }

    @Override
    public void paintControl(PaintEvent e) {
        GC gc = e.gc;
        Rectangle c = ((Control) e.widget).getBounds();
        gc.setLineWidth(lineWidth);
        gc.setForeground(ResourceManager.getColor(this.color));
        gc.drawRectangle(lineWidth / 2, lineWidth / 2, c.width - lineWidth, c.height - lineWidth);
        gc.dispose();
    }
}

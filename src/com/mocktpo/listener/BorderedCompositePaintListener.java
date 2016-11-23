package com.mocktpo.listener;

import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public class BorderedCompositePaintListener implements PaintListener {

    @Override
    public void paintControl(PaintEvent e) {
        GC gc = e.gc;
        Rectangle c = ((Control) e.widget).getBounds();
        gc.setBackground(ResourceManager.getColor(MT.COLOR_BORDER));
        gc.drawRectangle(1, 1, c.width - 2, c.height - 2);
    }
}

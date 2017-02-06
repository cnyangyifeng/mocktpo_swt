package com.mocktpo.events;

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
        gc.setForeground(ResourceManager.getColor(MT.COLOR_GRAY40));
        gc.drawRectangle(0, 0, c.width - 1, c.height - 1);
    }
}

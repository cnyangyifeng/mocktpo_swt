package com.mocktpo.modules.test.widgets;

import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class TestHeader extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Image backgroundImage;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestHeader(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        backgroundImage = new Image(d, ScreenUtils.getClientWidth(d), LC.TEST_HEADER_HEIGHT);
        GC gc = new GC(backgroundImage);

        Color start = ResourceManager.getColor(MT.COLOR_DARK_BLUE);
        Color end = ResourceManager.getColor(MT.COLOR_BURGUNDY);

        gc.setForeground(start);
        gc.setBackground(end);
        gc.fillGradientRectangle(0, 0, ScreenUtils.getClientWidth(d), LC.TEST_HEADER_HEIGHT, false);
        gc.dispose();

        CompositeSet.decorate(this).setBackgroundImage(backgroundImage);

        this.addDisposeListener(new TestHeaderDisposeListener());
    }

    private class TestHeaderDisposeListener implements DisposeListener {

        @Override
        public void widgetDisposed(DisposeEvent e) {
            if (backgroundImage != null && !backgroundImage.isDisposed()) {
                backgroundImage.dispose();
            }
        }
    }
}

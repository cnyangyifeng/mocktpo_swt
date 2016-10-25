package com.mocktpo.widget;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;

public class ImageButton extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Image normal;
    private Image hover;

    public ImageButton(Composite parent, int style, Image normal, Image hover) {
        super(parent, style);
        this.normal = normal;
        this.hover = hover;
        setBackgroundImage(normal);
        setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        addMouseTrackListener(new ImageButtonMouseTrackListener());
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    @Override
    public Point computeSize(int wh, int hh, boolean changed) {
        int width = 0, height = 0;
        if (null != normal) {
            Rectangle bounds = normal.getBounds();
            width = bounds.width;
            height = bounds.height;
        }
        return new Point(width, height);
    }

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

    private class ImageButtonMouseTrackListener implements MouseTrackListener {

        @Override
        public void mouseEnter(MouseEvent e) {
            setBackgroundImage(hover);
        }

        @Override
        public void mouseExit(MouseEvent e) {
            setBackgroundImage(normal);
        }

        @Override
        public void mouseHover(MouseEvent e) {
        }
    }
}

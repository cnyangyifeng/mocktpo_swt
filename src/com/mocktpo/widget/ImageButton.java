package com.mocktpo.widget;

import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class ImageButton extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Image normal;
    private Image hover;
    private Image disabled;

    public ImageButton(Composite parent, int style, int normal, int hover) {
        this(parent, style, ResourceManager.getImage(normal), ResourceManager.getImage(hover), null);
    }

    public ImageButton(Composite parent, int style, int normal, int hover, int disabled) {
        this(parent, style, ResourceManager.getImage(normal), ResourceManager.getImage(hover), ResourceManager.getImage(disabled));
    }

    public ImageButton(Composite parent, int style, Image normal, Image hover) {
        this(parent, style, normal, hover, null);
    }

    public ImageButton(Composite parent, int style, Image normal, Image hover, Image disabled) {
        super(parent, style);
        this.normal = normal;
        this.hover = hover;
        this.disabled = disabled;
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
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        if (b) {
            setBackgroundImage(normal);
        } else {
            if (null != disabled) {
                setBackgroundImage(disabled);
            }
        }
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
     * Background Images
     * 
     **************************************************/

    public void setBackgroundImages(int normal, int hover) {
        this.setBackgroundImages(ResourceManager.getImage(normal), ResourceManager.getImage(hover), null);
    }

    public void setBackgroundImages(int normal, int hover, int disabled) {
        this.setBackgroundImages(ResourceManager.getImage(normal), ResourceManager.getImage(hover), ResourceManager.getImage(disabled));
    }

    public void setBackgroundImages(Image normal, Image hover) {
        this.setBackgroundImages(normal, hover, null);
    }

    public void setBackgroundImages(Image normal, Image hover, Image disabled) {
        this.normal = normal;
        this.hover = hover;
        this.disabled = disabled;
        this.setBackgroundImage(this.normal);
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

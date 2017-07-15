package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
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

    /* Listeners */

    private PaintListener paintListener;

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
        this.d = parent.getDisplay();
        this.normal = normal;
        this.hover = hover;
        this.disabled = disabled;
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        setTransparentBackgroundImage(normal);
        addMouseTrackListener(new ImageButtonMouseTrackListener());
    }

    @Override
    public void setEnabled(boolean b) {
        if (b) {
            setTransparentBackgroundImage(normal);
        } else {
            if (disabled != null) {
                setTransparentBackgroundImage(disabled);
            }
        }
        super.setEnabled(b);
    }

    @Override
    public Point computeSize(int wh, int hh, boolean changed) {
        int width = 0, height = 0;
        if (normal != null) {
            Rectangle bounds = normal.getBounds();
            width = bounds.width;
            height = bounds.height;
        }
        return new Point(width, height);
    }

    /*
     * ==================================================
     *
     * Background Images
     *
     * ==================================================
     */

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
        this.setTransparentBackgroundImage(this.normal);
    }

    private void setTransparentBackgroundImage(final Image image) {
        if (this.paintListener != null) {
            this.removePaintListener(this.paintListener);
        }
        this.paintListener = new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                e.gc.drawImage(image, 0, 0);
            }
        };
        this.addPaintListener(paintListener);
        this.redraw();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ImageButtonMouseTrackListener implements MouseTrackListener {

        @Override
        public void mouseEnter(MouseEvent e) {
            if (isEnabled()) {
                setTransparentBackgroundImage(hover);
            }
        }

        @Override
        public void mouseExit(MouseEvent e) {
            if (isEnabled()) {
                setTransparentBackgroundImage(normal);
            }
        }

        @Override
        public void mouseHover(MouseEvent e) {
        }
    }
}

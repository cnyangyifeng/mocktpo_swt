package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import java.util.ResourceBundle;

public class ImageButton extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Widgets */

    private Image normal;
    private Image hover;
    private Image disabled;

    /* Listeners */

    private PaintListener paintListener;

    public ImageButton(Composite parent, int style, String normal, String hover) {
        this(parent, style, ResourceManager.getImage(normal), ResourceManager.getImage(hover), null);
    }

    public ImageButton(Composite parent, int style, String normal, String hover, String disabled) {
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
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        setTransparentBackgroundImage(normal);
        addMouseTrackListener(new ImageButtonMouseTrackAdapter());
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

    public void setBackgroundImages(String normal, String hover) {
        this.setBackgroundImages(ResourceManager.getImage(normal), ResourceManager.getImage(hover), null);
    }

    public void setBackgroundImages(String normal, String hover, String disabled) {
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
        this.paintListener = (e) -> e.gc.drawImage(image, 0, 0);
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

    private class ImageButtonMouseTrackAdapter extends MouseTrackAdapter {

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
    }
}

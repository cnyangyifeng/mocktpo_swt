package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class CompositeSet {

    private Composite c;

    public static CompositeSet decorate(Composite c) {
        return new CompositeSet(c);
    }

    private CompositeSet(Composite c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Background Settings
     *
     * ==================================================
     */

    public CompositeSet setBackground(String i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public CompositeSet setBackground(Color color) {
        c.setBackground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Background Image Settings
     *
     * ==================================================
     */

    public CompositeSet setBackgroundImage(String i) {
        c.setBackgroundImage(ResourceManager.getImage(i));
        return this;
    }

    public CompositeSet setBackgroundImage(Image image) {
        c.setBackgroundImage(image);
        return this;
    }

    /*
     * ==================================================
     *
     * Cursor Settings
     *
     * ==================================================
     */

    public CompositeSet setCursor(String i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public CompositeSet setCursor(Cursor cursor) {
        c.setCursor(cursor);
        return this;
    }

    /*
     * ==================================================
     *
     * Data Settings
     *
     * ==================================================
     */

    public CompositeSet setData(String key, Object value) {
        c.setData(key, value);
        return this;
    }

    /*
     * ==================================================
     *
     * Enable Settings
     *
     * ==================================================
     */

    public CompositeSet setEnabled(boolean b) {
        c.setEnabled(b);
        return this;
    }

    /*
     * ==================================================
     *
     * Focus Settings
     *
     * ==================================================
     */

    public CompositeSet setFocus() {
        c.setFocus();
        return this;
    }

    /*
     * ==================================================
     *
     * Font Settings
     *
     * ==================================================
     */

    public CompositeSet setFont(String i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public CompositeSet setFont(Font f) {
        c.setFont(f);
        return this;
    }

    /*
     * ==================================================
     *
     * Foreground Settings
     *
     * ==================================================
     */

    public CompositeSet setForeground(String i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public CompositeSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Visible Settings
     *
     * ==================================================
     */

    public CompositeSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

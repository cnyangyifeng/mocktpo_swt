package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Scale;

public class ScaleSet {

    private Scale c;

    public static ScaleSet decorate(Scale c) {
        return new ScaleSet(c);
    }

    private ScaleSet(Scale c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Background Settings
     *
     * ==================================================
     */

    public ScaleSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public ScaleSet setBackground(Color color) {
        c.setBackground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Cursor Settings
     *
     * ==================================================
     */

    public ScaleSet setCursor(int i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public ScaleSet setCursor(Cursor cursor) {
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

    public ScaleSet setData(String key, Object value) {
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

    public ScaleSet setEnabled(boolean b) {
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

    public ScaleSet setFocus() {
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

    public ScaleSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public ScaleSet setFont(Font f) {
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

    public ScaleSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public ScaleSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Increment Settings
     *
     * ==================================================
     */

    public ScaleSet setIncrement(int i) {
        c.setIncrement(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Maximum Settings
     *
     * ==================================================
     */

    public ScaleSet setMaximum(int i) {
        c.setMaximum(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Minimum Settings
     *
     * ==================================================
     */

    public ScaleSet setMinimum(int i) {
        c.setMinimum(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Page Increment Settings
     *
     * ==================================================
     */

    public ScaleSet setPageIncrement(int i) {
        c.setPageIncrement(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Selection Settings
     *
     * ==================================================
     */

    public ScaleSet setSelection(int i) {
        c.setSelection(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Visible Settings
     *
     * ==================================================
     */

    public ScaleSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

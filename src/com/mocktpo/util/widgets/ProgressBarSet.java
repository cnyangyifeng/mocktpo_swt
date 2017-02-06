package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.ProgressBar;

public class ProgressBarSet {

    private ProgressBar c;

    public static ProgressBarSet decorate(ProgressBar c) {
        return new ProgressBarSet(c);
    }

    private ProgressBarSet(ProgressBar c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Background Settings
     *
     * ==================================================
     */

    public ProgressBarSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public ProgressBarSet setBackground(Color color) {
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

    public ProgressBarSet setCursor(int i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public ProgressBarSet setCursor(Cursor cursor) {
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

    public ProgressBarSet setData(String key, Object value) {
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

    public ProgressBarSet setEnabled(boolean b) {
        c.setEnabled(b);
        return this;
    }

    /*
     * ==================================================
     *
     * Font Settings
     *
     * ==================================================
     */

    public ProgressBarSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public ProgressBarSet setFont(Font f) {
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

    public ProgressBarSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public ProgressBarSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Maximum Settings
     *
     * ==================================================
     */

    public ProgressBarSet setMaximum(int i) {
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

    public ProgressBarSet setMinimum(int i) {
        c.setMinimum(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Selection Settings
     *
     * ==================================================
     */

    public ProgressBarSet setSelection(int i) {
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

    public ProgressBarSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

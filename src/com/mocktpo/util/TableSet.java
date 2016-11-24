package com.mocktpo.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Table;

public class TableSet {

    private Table c;

    public static TableSet decorate(Table c) {
        return new TableSet(c);
    }

    private TableSet(Table c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Background Settings
     *
     * ==================================================
     */

    public TableSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public TableSet setBackground(Color color) {
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

    public TableSet setCursor(int i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public TableSet setCursor(Cursor cursor) {
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

    public TableSet setData(String key, Object value) {
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

    public TableSet setEnabled(boolean b) {
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

    public TableSet setFocus() {
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

    public TableSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public TableSet setFont(Font f) {
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

    public TableSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public TableSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Line Visible Settings
     *
     * ==================================================
     */

    public TableSet setLineVisible(boolean b) {
        c.setLinesVisible(b);
        return this;
    }

    /*
     * ==================================================
     *
     * Visible Settings
     *
     * ==================================================
     */

    public TableSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

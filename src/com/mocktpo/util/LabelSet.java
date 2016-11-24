package com.mocktpo.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;

public class LabelSet {

    private Label c;

    public static LabelSet decorate(Label c) {
        return new LabelSet(c);
    }

    private LabelSet(Label c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Alignment Settings
     *
     * ==================================================
     */

    public LabelSet setAlignment(int i) {
        c.setAlignment(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Background Settings
     *
     * ==================================================
     */

    public LabelSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public LabelSet setBackground(Color color) {
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

    public LabelSet setCursor(int i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public LabelSet setCursor(Cursor cursor) {
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

    public LabelSet setData(String key, Object value) {
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

    public LabelSet setEnabled(boolean b) {
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

    public LabelSet setFocus() {
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

    public LabelSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public LabelSet setFont(Font f) {
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

    public LabelSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public LabelSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Image Settings
     *
     * ==================================================
     */

    public LabelSet setImage(int i) {
        c.setImage(ResourceManager.getImage(i));
        return this;
    }

    public LabelSet setImage(Image image) {
        c.setImage(image);
        return this;
    }

    /*
     * ==================================================
     *
     * Text Settings
     *
     * ==================================================
     */

    public LabelSet setText(String text) {
        c.setText(text);
        return this;
    }

    /*
     * ==================================================
     *
     * Visible Settings
     *
     * ==================================================
     */

    public LabelSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

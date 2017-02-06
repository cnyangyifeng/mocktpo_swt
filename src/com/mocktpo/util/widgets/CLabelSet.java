package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public class CLabelSet {

    private CLabel c;

    public static CLabelSet decorate(CLabel c) {
        return new CLabelSet(c);
    }

    private CLabelSet(CLabel c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Alignment Settings
     *
     * ==================================================
     */

    public CLabelSet setAlignment(int i) {
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

    public CLabelSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public CLabelSet setBackground(Color color) {
        c.setBackground(color);
        return this;
    }

    public CLabelSet setGradientBackground(int start, int end, boolean vertical) {
        Color sc = ResourceManager.getColor(start);
        Color ec = ResourceManager.getColor(end);
        c.setBackground(new Color[]{sc, ec}, new int[]{100}, vertical);
        return this;
    }

    public CLabelSet setGradientBackground(Color start, Color end, boolean vertical) {
        c.setBackground(new Color[]{start, end}, new int[]{100}, vertical);
        return this;
    }

    /*
     * ==================================================
     *
     * Cursor Settings
     *
     * ==================================================
     */

    public CLabelSet setCursor(int i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public CLabelSet setCursor(Cursor cursor) {
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

    public CLabelSet setData(String key, Object value) {
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

    public CLabelSet setEnabled(boolean b) {
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

    public CLabelSet setFocus() {
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

    public CLabelSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public CLabelSet setFont(Font f) {
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

    public CLabelSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public CLabelSet setForeground(Color color) {
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

    public CLabelSet setImage(int i) {
        c.setImage(ResourceManager.getImage(i));
        return this;
    }

    public CLabelSet setImage(Image image) {
        c.setImage(image);
        return this;
    }

    /*
     * ==================================================
     *
     * Margins Settings
     *
     * ==================================================
     */

    public CLabelSet setMargins(int margin) {
        c.setMargins(margin, margin, margin, margin);
        return this;
    }

    public CLabelSet setMargins(int left, int top, int right, int bottom) {
        c.setMargins(left, top, right, bottom);
        return this;
    }

    public CLabelSet setMarginWidth(int margin) {
        c.setLeftMargin(margin);
        c.setRightMargin(margin);
        return this;
    }

    public CLabelSet setMarginHeight(int margin) {
        c.setTopMargin(margin);
        c.setBottomMargin(margin);
        return this;
    }

    public CLabelSet setLeftMargin(int left) {
        c.setLeftMargin(left);
        return this;
    }

    public CLabelSet setTopMargin(int top) {
        c.setTopMargin(top);
        return this;
    }

    public CLabelSet setRightMargin(int right) {
        c.setRightMargin(right);
        return this;
    }

    public CLabelSet setBottomMargin(int bottom) {
        c.setBottomMargin(bottom);
        return this;
    }

    /*
     * ==================================================
     *
     * Text Settings
     *
     * ==================================================
     */

    public CLabelSet setText(String text) {
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

    public CLabelSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

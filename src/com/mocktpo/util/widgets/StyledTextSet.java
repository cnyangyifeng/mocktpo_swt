package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Caret;

public class StyledTextSet {

    private StyledText c;

    public static StyledTextSet decorate(StyledText c) {
        return new StyledTextSet(c);
    }

    private StyledTextSet(StyledText c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Alignment Settings
     *
     * ==================================================
     */

    public StyledTextSet setAlignment(int i) {
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

    public StyledTextSet setBackground(String i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public StyledTextSet setBackground(Color color) {
        c.setBackground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Caret Settings
     *
     * ==================================================
     */

    public StyledTextSet setNoCaret() {
        c.setCaret(null);
        return this;
    }

    public StyledTextSet setCaret(Caret caret) {
        c.setCaret(caret);
        return this;
    }

    /*
     * ==================================================
     *
     * Cursor Settings
     *
     * ==================================================
     */

    public StyledTextSet setCursor(String i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public StyledTextSet setCursor(Cursor cursor) {
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

    public StyledTextSet setData(String key, Object value) {
        c.setData(key, value);
        return this;
    }

    /*
     * ==================================================
     *
     * Editable Settings
     *
     * ==================================================
     */

    public StyledTextSet setEditable(boolean b) {
        c.setEditable(b);
        return this;
    }

    /*
     * ==================================================
     *
     * Enable Settings
     *
     * ==================================================
     */

    public StyledTextSet setEnabled(boolean b) {
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

    public StyledTextSet setFocus() {
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

    public StyledTextSet setFont(String i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public StyledTextSet setFont(Font f) {
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

    public StyledTextSet setForeground(String i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public StyledTextSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Line Spacing Settings
     *
     * ==================================================
     */

    public StyledTextSet setLineSpacing(int i) {
        c.setLineSpacing(i);
        return this;
    }

    /*
     * ==================================================
     *
     * Margins Settings
     *
     * ==================================================
     */

    public StyledTextSet setMargins(int margin) {
        c.setMargins(margin, margin, margin, margin);
        return this;
    }

    public StyledTextSet setMargins(int left, int top, int right, int bottom) {
        c.setMargins(left, top, right, bottom);
        return this;
    }

    public StyledTextSet setMarginWidth(int margin) {
        c.setLeftMargin(margin);
        c.setRightMargin(margin);
        return this;
    }

    public StyledTextSet setMarginHeight(int margin) {
        c.setTopMargin(margin);
        c.setBottomMargin(margin);
        return this;
    }

    public StyledTextSet setLeftMargin(int left) {
        c.setLeftMargin(left);
        return this;
    }

    public StyledTextSet setTopMargin(int top) {
        c.setTopMargin(top);
        return this;
    }

    public StyledTextSet setRightMargin(int right) {
        c.setRightMargin(right);
        return this;
    }

    public StyledTextSet setBottomMargin(int bottom) {
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

    public StyledTextSet setText(String text) {
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

    public StyledTextSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

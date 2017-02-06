package com.mocktpo.util.widgets;

import com.mocktpo.util.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;

public class ButtonSet {

    private Button c;

    public static ButtonSet decorate(Button c) {
        return new ButtonSet(c);
    }

    private ButtonSet(Button c) {
        this.c = c;
    }

    /*
     * ==================================================
     *
     * Alignment Settings
     *
     * ==================================================
     */

    public ButtonSet setAlignment(int i) {
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

    public ButtonSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public ButtonSet setBackground(Color color) {
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

    public ButtonSet setCursor(int i) {
        c.setCursor(ResourceManager.getCursor(i));
        return this;
    }

    public ButtonSet setCursor(Cursor cursor) {
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

    public ButtonSet setData(String key, Object value) {
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

    public ButtonSet setEnabled(boolean b) {
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

    public ButtonSet setFocus() {
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

    public ButtonSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public ButtonSet setFont(Font f) {
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

    public ButtonSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public ButtonSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /*
     * ==================================================
     *
     * Text Settings
     *
     * ==================================================
     */

    public ButtonSet setText(String text) {
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

    public ButtonSet setVisible(boolean b) {
        c.setVisible(b);
        return this;
    }
}

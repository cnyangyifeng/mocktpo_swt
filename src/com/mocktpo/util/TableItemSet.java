package com.mocktpo.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableItem;

public class TableItemSet {

    private TableItem c;

    public static TableItemSet decorate(TableItem c) {
        return new TableItemSet(c);
    }

    private TableItemSet(TableItem c) {
        this.c = c;
    }

    /**************************************************
     * 
     * Background Settings
     * 
     **************************************************/

    public TableItemSet setBackground(int i) {
        c.setBackground(ResourceManager.getColor(i));
        return this;
    }

    public TableItemSet setBackground(Color color) {
        c.setBackground(color);
        return this;
    }

    /**************************************************
     * 
     * Data Settings
     * 
     **************************************************/

    public TableItemSet setData(String key, Object value) {
        c.setData(key, value);
        return this;
    }

    /**************************************************
     * 
     * Font Settings
     * 
     **************************************************/

    public TableItemSet setFont(int i) {
        c.setFont(ResourceManager.getFont(i));
        return this;
    }

    public TableItemSet setFont(Font f) {
        c.setFont(f);
        return this;
    }

    /**************************************************
     * 
     * Foreground Settings
     * 
     **************************************************/

    public TableItemSet setForeground(int i) {
        c.setForeground(ResourceManager.getColor(i));
        return this;
    }

    public TableItemSet setForeground(Color color) {
        c.setForeground(color);
        return this;
    }

    /**************************************************
     * 
     * Image Settings
     * 
     **************************************************/

    public TableItemSet setImage(int i) {
        c.setImage(ResourceManager.getImage(i));
        return this;
    }

    public TableItemSet setImage(Image image) {
        c.setImage(image);
        return this;
    }

    /**************************************************
     * 
     * Text Settings
     * 
     **************************************************/

    public TableItemSet setText(String text) {
        c.setText(text);
        return this;
    }

    public TableItemSet setText(String[] texts) {
        c.setText(texts);
        return this;
    }
}

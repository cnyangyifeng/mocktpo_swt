package com.mocktpo.vo;

import java.io.Serializable;

public class StyleRangeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int start;
    private int length;
    private int fontStyle;
    private int foreground;
    private int background;
    private boolean underline;
    private String image;

    public StyleRangeVo() {
    }

    public StyleRangeVo(int start, int length, int fontStyle, int foreground, int background, boolean underline, String image) {
        this.start = start;
        this.length = length;
        this.fontStyle = fontStyle;
        this.foreground = foreground;
        this.background = background;
        this.underline = underline;
        this.image = image;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getForeground() {
        return foreground;
    }

    public void setForeground(int foreground) {
        this.foreground = foreground;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

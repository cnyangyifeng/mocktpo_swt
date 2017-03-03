package com.mocktpo.vo;

import com.mocktpo.util.constants.MT;
import org.apache.commons.lang3.StringUtils;

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

    public StyleRangeVo(String style) {
        if (StringUtils.isEmpty(style)) {
            return;
        }
        String[] arr = style.split(MT.STRING_COMMA);
        this.start = Integer.parseInt(arr[0]);
        this.length = Integer.parseInt(arr[1]);
        this.fontStyle = Integer.parseInt(arr[2]);
        this.foreground = Integer.parseInt(arr[3]);
        this.background = Integer.parseInt(arr[4]);
        this.underline = Boolean.parseBoolean(arr[5]);
        this.image = null;
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

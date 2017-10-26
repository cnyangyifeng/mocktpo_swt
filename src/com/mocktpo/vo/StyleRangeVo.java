package com.mocktpo.vo;

import com.mocktpo.util.constants.MT;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class StyleRangeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int start;
    private int length;
    private int fontStyle;
    private String foreground;
    private String background;
    private boolean underline;
    private String image;

    public StyleRangeVo() {
    }

    public StyleRangeVo(int start, int length, int fontStyle, String foreground, String background, boolean underline, String image) {
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
        this.foreground = arr[3];
        this.background = arr[4];
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

    public String getForeground() {
        return foreground;
    }

    public void setForeground(String foreground) {
        this.foreground = foreground;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
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

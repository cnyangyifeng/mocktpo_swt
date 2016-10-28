package com.mocktpo.vo;

import java.io.Serializable;
import java.util.List;

public class StyledTextVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;
    private List<StyleRangeVo> styles;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<StyleRangeVo> getStyles() {
        return styles;
    }

    public void setStyles(List<StyleRangeVo> styles) {
        this.styles = styles;
    }
}

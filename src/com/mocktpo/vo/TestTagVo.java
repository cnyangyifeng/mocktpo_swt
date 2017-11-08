package com.mocktpo.vo;

import java.io.Serializable;

public class TestTagVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int tagId;
    private String title;
    private int status;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

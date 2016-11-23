package com.mocktpo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestSchemaVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CACHE_SIZE = 64;

    private List<TestViewVo> views = new ArrayList<TestViewVo>(INITIAL_CACHE_SIZE);

    private int tid;
    private String title;

    public TestViewVo getView(int viewId) {
        for (TestViewVo v : views) {
            if (viewId == v.getViewId()) {
                return v;
            }
        }
        return null;
    }

    public List<TestViewVo> getViews() {
        return views;
    }

    public void setViews(List<TestViewVo> views) {
        this.views = views;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.mocktpo.orm.domain;

public class UserTestAnswer {

    private long sid;
    private int viewId;
    private int sectionType;
    private String answer;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getSectionType() {
        return sectionType;
    }

    public void setSectionType(int sectionType) {
        this.sectionType = sectionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

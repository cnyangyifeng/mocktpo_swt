package com.mocktpo.orm.domain;

public class UserTestAnswer {

    private int sid;
    private int viewId;
    private int sectionType;
    private String answer;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getViewId() {
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

    @Override
    public String toString() {
        return "{\nsid:" + this.getSid() + ",\nviewId:" + this.getViewId() + ",\nsectionType:" + this.getSectionType() + ",\nanswer:" + this.getAnswer() + "\n}";
    }
}

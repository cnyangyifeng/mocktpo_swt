package com.mocktpo.orm.domain;

public class TestPaper {

    private String tid;
    private String title;
    private int stars;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "{\ntid:" + this.getTid() + ",\ntitle:" + this.getTitle() + "\n,stars:" + this.getStars() + "\n}";
    }
}

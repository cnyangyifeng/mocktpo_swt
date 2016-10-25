package com.mocktpo.orm.domain;

public class UserTest {

    private int tid;
    private String title;
    private String userName;
    private int lastViewId;
    private int progress;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLastViewId() {
        return lastViewId;
    }

    public void setLastViewId(int lastViewId) {
        this.lastViewId = lastViewId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "{\ntid:" + this.getTid() + ";\ntitle:" + this.getTitle() + ";\nuserName:" + this.getUserName() + ";\nlastViewId:" + this.getLastViewId() + ";\nprogress:" + this.getProgress() + "\n}";
    }
}

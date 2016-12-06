package com.mocktpo.orm.domain;

import com.mocktpo.util.constants.ST;

public class UserTest {

    private String email;
    private int tid;
    private String title;
    private String alias;
    private boolean timerHidden;
    private int readingTime;
    private int listeningTime;
    private int lastViewId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isTimerHidden() {
        return timerHidden;
    }

    public void setTimerHidden(boolean timerHidden) {
        this.timerHidden = timerHidden;
    }

    public int getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(int readingTime) {
        this.readingTime = readingTime;
    }

    public int getListeningTime() {
        return listeningTime;
    }

    public void setListeningTime(int listeningTime) {
        this.listeningTime = listeningTime;
    }

    public int getLastViewId() {
        return lastViewId;
    }

    public void setLastViewId(int lastViewId) {
        this.lastViewId = lastViewId;
    }

    public int getRemainingViewTime(int sectionType) {

        int viewTime = 0;

        switch (sectionType) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_LISTENING:
                viewTime = this.getListeningTime();
                break;
            case ST.SECTION_TYPE_READING:
                viewTime = this.getReadingTime();
                break;
            case ST.SECTION_TYPE_SPEAKING:
                break;
            case ST.SECTION_TYPE_WRITING:
                break;
        }

        return viewTime;
    }

    public void setRemainingViewTime(int sectionType, int viewTime) {

        switch (sectionType) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_LISTENING:
                this.setListeningTime(viewTime);
                break;
            case ST.SECTION_TYPE_READING:
                this.setReadingTime(viewTime);
                break;
            case ST.SECTION_TYPE_SPEAKING:
                break;
            case ST.SECTION_TYPE_WRITING:
                break;
        }
    }

    @Override
    public String toString() {
        return "{\nemail:" + this.getEmail() + ",\ntid:" + this.getTid() + ",\ntitle:" + this.getTitle() + ",\nalias:" + this.getAlias() + ",\nreadingTime:" + this.getReadingTime() + ",\nlastViewId:" + this.getLastViewId() + "\n}";
    }
}

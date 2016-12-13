package com.mocktpo.orm.domain;

import com.mocktpo.util.constants.ST;

public class UserTest {

    private String email;
    private int tid;
    private String title;
    private String alias;
    private boolean timerHidden;
    private int readingTime;
    private int listeningTime1;
    private int listeningTime2;
    private double volume;
    private boolean volumeControlHidden;
    private int completionRate;
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

    public int getListeningTime1() {
        return listeningTime1;
    }

    public void setListeningTime1(int listeningTime1) {
        this.listeningTime1 = listeningTime1;
    }


    public int getListeningTime2() {
        return listeningTime2;
    }

    public void setListeningTime2(int listeningTime2) {
        this.listeningTime2 = listeningTime2;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isVolumeControlHidden() {
        return volumeControlHidden;
    }

    public void setVolumeControlHidden(boolean volumeControlHidden) {
        this.volumeControlHidden = volumeControlHidden;
    }

    public int getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(int completionRate) {
        this.completionRate = completionRate;
    }

    public int getLastViewId() {
        return lastViewId;
    }

    public void setLastViewId(int lastViewId) {
        this.lastViewId = lastViewId;
    }

    public int getRemainingViewTime(int sectionType, int groupId) {

        int viewTime = 0;

        switch (sectionType) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                viewTime = this.getReadingTime();
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (groupId) {
                    case 1:
                        viewTime = this.getListeningTime1();
                        break;
                    case 2:
                        viewTime = this.getListeningTime2();
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                break;
            case ST.SECTION_TYPE_WRITING:
                break;
        }

        return viewTime;
    }

    public void setRemainingViewTime(int sectionType, int groupId, int viewTime) {

        switch (sectionType) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                this.setReadingTime(viewTime);
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (groupId) {
                    case 1:
                        this.setListeningTime1(viewTime);
                        break;
                    case 2:
                        this.setListeningTime2(viewTime);
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                break;
            case ST.SECTION_TYPE_WRITING:
                break;
        }
    }

    @Override
    public String toString() {
        return "{\nemail:" + this.getEmail() + ",\ntid:" + this.getTid() + ",\ntitle:" + this.getTitle() + ",\nalias:" + this.getAlias() + ",\ntimerHidden:" + this.isTimerHidden() + ",\nreadingTime:" + this.getReadingTime() + ",\nlisteningTime1:" + this.getListeningTime1() + ",\nlisteningTime2:" + this.getListeningTime2() + ",\nvolume:" + this.getVolume() + ",\nvolumeControlHidden:" + this.isVolumeControlHidden() + ",\ncompletionRate:" + this.getCompletionRate() + ",\nlastViewId:" + this.getLastViewId() + "\n}";
    }
}

package com.mocktpo.orm.domain;

import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.TestViewVo;

public class UserTestSession {

    private int sid;
    private int tid;
    private String title;
    private String fileAlias;
    private boolean timerHidden;
    private int readingTime;
    private int listeningTime1;
    private int listeningTime2;
    private int speakingReadingTime1;
    private int speakingReadingTime2;
    private int writingReadingTime;
    private int integratedWritingTime;
    private int independentWritingTime;
    private double volume;
    private boolean volumeControlHidden;
    private int stars;
    private int lastViewId;
    private int maxViewId;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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

    public String getFileAlias() {
        return fileAlias;
    }

    public void setFileAlias(String fileAlias) {
        this.fileAlias = fileAlias;
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

    public int getSpeakingReadingTime1() {
        return speakingReadingTime1;
    }

    public void setSpeakingReadingTime1(int speakingReadingTime1) {
        this.speakingReadingTime1 = speakingReadingTime1;
    }

    public int getSpeakingReadingTime2() {
        return speakingReadingTime2;
    }

    public void setSpeakingReadingTime2(int speakingReadingTime2) {
        this.speakingReadingTime2 = speakingReadingTime2;
    }

    public int getWritingReadingTime() {
        return writingReadingTime;
    }

    public void setWritingReadingTime(int writingReadingTime) {
        this.writingReadingTime = writingReadingTime;
    }

    public int getIntegratedWritingTime() {
        return integratedWritingTime;
    }

    public void setIntegratedWritingTime(int integratedWritingTime) {
        this.integratedWritingTime = integratedWritingTime;
    }

    public int getIndependentWritingTime() {
        return independentWritingTime;
    }

    public void setIndependentWritingTime(int independentWritingTime) {
        this.independentWritingTime = independentWritingTime;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getLastViewId() {
        return lastViewId;
    }

    public void setLastViewId(int lastViewId) {
        this.lastViewId = lastViewId;
    }

    public int getMaxViewId() {
        return maxViewId;
    }

    public void setMaxViewId(int maxViewId) {
        if (maxViewId > this.maxViewId) {
            this.maxViewId = maxViewId;
        }
    }

    /*
     * ==================================================
     *
     * Remaining View Time
     *
     * ==================================================
     */

    public int getRemainingViewTime(TestViewVo vo) {
        int viewTime = 0;
        switch (vo.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                viewTime = this.getReadingTime();
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (vo.getGroupId()) {
                    case 1:
                        viewTime = this.getListeningTime1();
                        break;
                    case 2:
                        viewTime = this.getListeningTime2();
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                switch (vo.getGroupId()) {
                    case 1:
                        viewTime = this.getSpeakingReadingTime1();
                        break;
                    case 2:
                        viewTime = this.getSpeakingReadingTime2();
                        break;
                }
                break;
            case ST.SECTION_TYPE_WRITING:
                switch (vo.getViewType()) {
                    case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                        viewTime = this.getWritingReadingTime();
                        break;
                    case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                        viewTime = this.getIntegratedWritingTime();
                        break;
                    case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK_END:
                        viewTime = this.getIntegratedWritingTime();
                        break;
                    case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                        viewTime = this.getIndependentWritingTime();
                        break;
                    case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK_END:
                        viewTime = this.getIndependentWritingTime();
                        break;
                }
                break;
        }

        return viewTime;
    }

    public void setRemainingViewTime(TestViewVo vo, int viewTime) {
        switch (vo.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                this.setReadingTime(viewTime);
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (vo.getGroupId()) {
                    case 1:
                        this.setListeningTime1(viewTime);
                        break;
                    case 2:
                        this.setListeningTime2(viewTime);
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                switch (vo.getGroupId()) {
                    case 1:
                        this.setSpeakingReadingTime1(viewTime);
                        break;
                    case 2:
                        this.setSpeakingReadingTime2(viewTime);
                        break;
                }
                break;
            case ST.SECTION_TYPE_WRITING:
                switch (vo.getViewType()) {
                    case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                        this.setWritingReadingTime(viewTime);
                        break;
                    case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                        this.setIntegratedWritingTime(viewTime);
                        break;
                    case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK_END:
                        this.setIntegratedWritingTime(viewTime);
                        break;
                    case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                        this.setIndependentWritingTime(viewTime);
                        break;
                    case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK_END:
                        this.setIndependentWritingTime(viewTime);
                        break;
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "{\nsid:" + this.getSid() + ",\ntid:" + this.getTid() + ",\ntitle:" + this.getTitle() + ",\nfileAlias:" + this.getFileAlias() + ",\ntimerHidden:" + this.isTimerHidden() + ",\nreadingTime:" + this.getReadingTime() + ",\nlisteningTime1:" + this.getListeningTime1() + ",\nlisteningTime2:" + this.getListeningTime2() + ",\nspeakingReadingTime1:" + this.getSpeakingReadingTime1() + ",\nspeakingReadingTime2:" + this.getSpeakingReadingTime2() + ",\nwritingReadingTime:" + this.getWritingReadingTime() + ",\nintegratedWritingTime:" + this.getIntegratedWritingTime() + ",\nindependentWritingTime:" + this.getIndependentWritingTime() + ",\nvolume:" + this.getVolume() + ",\nvolumeControlHidden:" + this.isVolumeControlHidden() + ",\nstars:" + this.getStars() + ",\nlastViewId:" + this.getLastViewId() + ",\nmaxViewId:" + this.getMaxViewId() + "\n}";
    }
}

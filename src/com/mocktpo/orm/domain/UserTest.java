package com.mocktpo.orm.domain;

import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.TestViewVo;

public class UserTest {

    private String email;
    private int tid;
    private String title;
    private String alias;
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

    /*
     * ==================================================
     *
     * Customized Methods
     *
     * ==================================================
     */

    public int getRemainingViewTime(TestViewVo tvv) {
        int viewTime = 0;
        switch (tvv.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                viewTime = this.getReadingTime();
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (tvv.getGroupId()) {
                    case 1:
                        viewTime = this.getListeningTime1();
                        break;
                    case 2:
                        viewTime = this.getListeningTime2();
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                switch (tvv.getGroupId()) {
                    case 1:
                        viewTime = this.getSpeakingReadingTime1();
                        break;
                    case 2:
                        viewTime = this.getSpeakingReadingTime2();
                        break;
                }
                break;
            case ST.SECTION_TYPE_WRITING:
                switch (tvv.getViewType()) {
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
            case ST.SECTION_TYPE_REPORT:
                break;
        }

        return viewTime;
    }

    public void setRemainingViewTime(TestViewVo tvv, int viewTime) {
        switch (tvv.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                this.setReadingTime(viewTime);
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (tvv.getGroupId()) {
                    case 1:
                        this.setListeningTime1(viewTime);
                        break;
                    case 2:
                        this.setListeningTime2(viewTime);
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                switch (tvv.getGroupId()) {
                    case 1:
                        this.setSpeakingReadingTime1(viewTime);
                        break;
                    case 2:
                        this.setSpeakingReadingTime2(viewTime);
                        break;
                }
                break;
            case ST.SECTION_TYPE_WRITING:
                switch (tvv.getViewType()) {
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
            case ST.SECTION_TYPE_REPORT:
                break;
        }
    }

    @Override
    public String toString() {
        return "{\nemail:" + this.getEmail() + ",\ntid:" + this.getTid() + ",\ntitle:" + this.getTitle() + ",\nalias:" + this.getAlias() + ",\ntimerHidden:" + this.isTimerHidden() + ",\nreadingTime:" + this.getReadingTime() + ",\nlisteningTime1:" + this.getListeningTime1() + ",\nlisteningTime2:" + this.getListeningTime2() + ",\nspeakingReadingTime1:" + this.getSpeakingReadingTime1() + ",\nspeakingReadingTime2:" + this.getSpeakingReadingTime2() + ",\nwritingReadingTime:" + this.getWritingReadingTime() + ",\nintegratedWritingTime:" + this.getIntegratedWritingTime() + ",\nindependentWritingTime:" + this.getIndependentWritingTime() + ",\nvolume:" + this.getVolume() + ",\nvolumeControlHidden:" + this.isVolumeControlHidden() + ",\ncompletionRate:" + this.getStars() + ",\nlastViewId:" + this.getLastViewId() + "\n}";
    }
}

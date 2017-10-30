package com.mocktpo.vo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestEditorVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    /* Properties */

    private List<TestViewVo> readingViewVos = new ArrayList<>();
    private List<TestViewVo> listeningViewVos = new ArrayList<>();
    private List<TestViewVo> speakingViewVos = new ArrayList<>();
    private List<TestViewVo> writingViewVos = new ArrayList<>();

    private String tid;
    private String title;
    private int stars;
    private String creator;
    private long createdTime;
    private long updatedTime;
    private double version;

    public List<TestViewVo> getReadingViewVos() {
        return readingViewVos;
    }

    public void setReadingViewVos(List<TestViewVo> readingViewVos) {
        this.readingViewVos = readingViewVos;
    }

    public List<TestViewVo> getListeningViewVos() {
        return listeningViewVos;
    }

    public void setListeningViewVos(List<TestViewVo> listeningViewVos) {
        this.listeningViewVos = listeningViewVos;
    }

    public List<TestViewVo> getSpeakingViewVos() {
        return speakingViewVos;
    }

    public void setSpeakingViewVos(List<TestViewVo> speakingViewVos) {
        this.speakingViewVos = speakingViewVos;
    }

    public List<TestViewVo> getWritingViewVos() {
        return writingViewVos;
    }

    public void setWritingViewVos(List<TestViewVo> writingViewVos) {
        this.writingViewVos = writingViewVos;
    }

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}

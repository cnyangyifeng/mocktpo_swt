package com.mocktpo.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestViewVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CACHE_SIZE = 64;

    private Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>(INITIAL_CACHE_SIZE);

    private int viewId;
    private int viewType;
    private String viewTypeName;
    private int sectionType;
    private String sectionTypeName;
    private boolean firstPassage;
    private boolean withQuestion;
    private int questionNumberInSection;
    private boolean timed;
    private int passageOffset;
    private boolean withAudio;
    private String audioFileName;
    private boolean withImages;
    private String imageFileNames;

    public StyledTextVo getStyledText(String key) {
        for (String i : body.keySet()) {
            if (i.equals(key)) {
                return body.get(i);
            }
        }
        return null;
    }

    public Map<String, StyledTextVo> getBody() {
        return body;
    }

    public void setBody(Map<String, StyledTextVo> body) {
        this.body = body;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getViewTypeName() {
        return viewTypeName;
    }

    public void setViewTypeName(String viewTypeName) {
        this.viewTypeName = viewTypeName;
    }

    public int getSectionType() {
        return sectionType;
    }

    public void setSectionType(int sectionType) {
        this.sectionType = sectionType;
    }

    public String getSectionTypeName() {
        return sectionTypeName;
    }

    public void setSectionTypeName(String sectionTypeName) {
        this.sectionTypeName = sectionTypeName;
    }

    public boolean isFirstPassage() {
        return firstPassage;
    }

    public void setFirstPassage(boolean firstPassage) {
        this.firstPassage = firstPassage;
    }

    public boolean isWithQuestion() {
        return withQuestion;
    }

    public void setWithQuestion(boolean withQuestion) {
        this.withQuestion = withQuestion;
    }

    public int getQuestionNumberInSection() {
        return questionNumberInSection;
    }

    public void setQuestionNumberInSection(int questionNumberInSection) {
        this.questionNumberInSection = questionNumberInSection;
    }

    public boolean isTimed() {
        return timed;
    }

    public void setTimed(boolean timed) {
        this.timed = timed;
    }

    public int getPassageOffset() {
        return passageOffset;
    }

    public void setPassageOffset(int passageOffset) {
        this.passageOffset = passageOffset;
    }

    public boolean isWithAudio() {
        return withAudio;
    }

    public void setWithAudio(boolean withAudio) {
        this.withAudio = withAudio;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public boolean isWithImages() {
        return withImages;
    }

    public void setWithImages(boolean withImages) {
        this.withImages = withImages;
    }

    public String getImageFileNames() {
        return imageFileNames;
    }

    public void setImageFileNames(String imageFileNames) {
        this.imageFileNames = imageFileNames;
    }
}

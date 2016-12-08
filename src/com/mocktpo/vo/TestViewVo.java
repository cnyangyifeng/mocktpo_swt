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
    private int passageOffset;
    private boolean withAudio;
    private String audio;
    private boolean audioVisualized;
    private long audioDuration;
    private String illustrations;
    private boolean audioAsyncExecutable;
    private boolean withQuestion;
    private int questionNumberInSection;
    private int totalAnswerCount;
    private boolean timed;
    private boolean timerTaskDelayed;

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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public boolean isAudioVisualized() {
        return audioVisualized;
    }

    public void setAudioVisualized(boolean audioVisualized) {
        this.audioVisualized = audioVisualized;
    }

    public long getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(long audioDuration) {
        this.audioDuration = audioDuration;
    }

    public String getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(String illustrations) {
        this.illustrations = illustrations;
    }

    public boolean isAudioAsyncExecutable() {
        return audioAsyncExecutable;
    }

    public void setAudioAsyncExecutable(boolean audioAsyncExecutable) {
        this.audioAsyncExecutable = audioAsyncExecutable;
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

    public int getTotalAnswerCount() {
        return totalAnswerCount;
    }

    public void setTotalAnswerCount(int totalAnswerCount) {
        this.totalAnswerCount = totalAnswerCount;
    }

    public boolean isTimed() {
        return timed;
    }

    public void setTimed(boolean timed) {
        this.timed = timed;
    }

    public boolean isTimerTaskDelayed() {
        return timerTaskDelayed;
    }

    public void setTimerTaskDelayed(boolean timerTaskDelayed) {
        this.timerTaskDelayed = timerTaskDelayed;
    }
}

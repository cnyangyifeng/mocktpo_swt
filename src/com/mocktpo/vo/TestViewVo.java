package com.mocktpo.vo;

import com.mocktpo.util.constants.VT;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestViewVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CACHE_SIZE = 64;

    private Map<String, StyledTextVo> body = new HashMap<>(INITIAL_CACHE_SIZE);

    private int viewId;
    private int viewType;
    private int sectionType;
    private String sectionTypeName;
    private int listeningGroupId;
    private int speakingReadingId;
    private boolean firstPassage;
    private int passageOffset;
    private boolean withAudio;
    private String audio;
    private long audioDuration;
    private String illustrations;
    private String preparationAudio;
    private String responseAudio;
    private String beepAudio;
    private int speakingReadingTime;
    private int preparationTime;
    private int responseTime;
    private boolean timed;
    private boolean timerTaskDelayed;
    private boolean timerButtonUnavailable;
    private boolean questionCaptionVisible;
    private int questionNumberInSection;
    private boolean answerable;
    private int totalAnswerCount;
    private String answerKey;

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

    public int getListeningGroupId() {
        return listeningGroupId;
    }

    public void setListeningGroupId(int listeningGroupId) {
        this.listeningGroupId = listeningGroupId;
    }

    public int getSpeakingReadingId() {
        return speakingReadingId;
    }

    public void setSpeakingReadingId(int speakingReadingId) {
        this.speakingReadingId = speakingReadingId;
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

    public String getPreparationAudio() {
        return preparationAudio;
    }

    public void setPreparationAudio(String preparationAudio) {
        this.preparationAudio = preparationAudio;
    }

    public String getResponseAudio() {
        return responseAudio;
    }

    public void setResponseAudio(String responseAudio) {
        this.responseAudio = responseAudio;
    }

    public String getBeepAudio() {
        return beepAudio;
    }

    public void setBeepAudio(String beepAudio) {
        this.beepAudio = beepAudio;
    }

    public int getSpeakingReadingTime() {
        return speakingReadingTime;
    }

    public void setSpeakingReadingTime(int speakingReadingTime) {
        this.speakingReadingTime = speakingReadingTime;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
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

    public boolean isTimerButtonUnavailable() {
        return timerButtonUnavailable;
    }

    public void setTimerButtonUnavailable(boolean timerButtonUnavailable) {
        this.timerButtonUnavailable = timerButtonUnavailable;
    }

    public boolean isQuestionCaptionVisible() {
        return questionCaptionVisible;
    }

    public void setQuestionCaptionVisible(boolean questionCaptionVisible) {
        this.questionCaptionVisible = questionCaptionVisible;
    }

    public int getQuestionNumberInSection() {
        return questionNumberInSection;
    }

    public void setQuestionNumberInSection(int questionNumberInSection) {
        this.questionNumberInSection = questionNumberInSection;
    }

    public boolean isAnswerable() {
        return answerable;
    }

    public void setAnswerable(boolean answerable) {
        this.answerable = answerable;
    }

    public int getTotalAnswerCount() {
        return totalAnswerCount;
    }

    public void setTotalAnswerCount(int totalAnswerCount) {
        this.totalAnswerCount = totalAnswerCount;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    /*
     * ==================================================
     *
     * View Description Getter
     *
     * ==================================================
     */

    public String getViewDescription() {

        String viewDescription;

        switch (viewType) {

            /* Reading Section View Types */

            case VT.VIEW_TYPE_READING_PASSAGE:
                viewDescription = getStyledTextContent("heading");
                break;
            case VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;
            case VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION:
                viewDescription = getStyledTextContent("insertText");
                break;
            case VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;
            case VT.VIEW_TYPE_READING_FILL_IN_A_TABLE_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;

            /* Listening Section View Types */

            case VT.VIEW_TYPE_LISTENING_MATERIAL:
                viewDescription = getAudio();
                break;
            case VT.VIEW_TYPE_LISTENING_MULTIPLE_CHOICE_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;
            case VT.VIEW_TYPE_LISTENING_MULTIPLE_RESPONSE_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;
            case VT.VIEW_TYPE_LISTENING_REPLAY:
                viewDescription = getAudio();
                break;
            case VT.VIEW_TYPE_LISTENING_SORT_EVENTS_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;
            case VT.VIEW_TYPE_LISTENING_CATEGORIZE_OBJECTS_QUESTION:
                viewDescription = getStyledTextContent("question");
                break;

            /* Speaking Section View Types */

            case VT.VIEW_TYPE_SPEAKING_TASK:
                viewDescription = getStyledTextContent("question");
                ;
                break;
            case VT.VIEW_TYPE_SPEAKING_READING_PASSAGE:
                viewDescription = getStyledTextContent("heading");
                break;
            case VT.VIEW_TYPE_SPEAKING_LISTENING_MATERIAL:
                viewDescription = getAudio();
                break;

            /* Writing Section View Types */

            case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                viewDescription = getStyledTextContent("heading");
                break;
            case VT.VIEW_TYPE_WRITING_LISTENING_MATERIAL:
                viewDescription = getAudio();
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                viewDescription = getStyledTextContent("question");
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                viewDescription = getStyledTextContent("question");
                break;
            default:
                viewDescription = "";
                break;
        }

        return viewDescription;
    }

    /*
     * ==================================================
     *
     * Body StyledTextVo Related Getters and Setters
     *
     * ==================================================
     */

    public StyledTextVo getStyledTextVo(String key) {
        Map<String, StyledTextVo> body = getBody();
        if (body != null) {
            return body.get(key);
        } else {
            return null;
        }
    }

    public void setStyledTextVo(String key, StyledTextVo textVo) {
        Map<String, StyledTextVo> body = getBody();
        if (body != null) {
            body.put(key, textVo);
        }
    }

    public String getStyledTextContent(String key) {
        StyledTextVo styledTextVo = getStyledTextVo(key);
        if (styledTextVo != null) {
            return styledTextVo.getText();
        }
        return "";
    }

    public List<StyleRangeVo> getStyledTextStyles(String key) {
        StyledTextVo styledTextVo = getStyledTextVo(key);
        if (styledTextVo != null) {
            return styledTextVo.getStyles();
        }
        return null;
    }

    @Override
    public String toString() {
        return "{\nviewId:" + this.getViewId() + ",\nviewType:" + this.getViewType() + ",\nsectionType:" + this.getSectionType() + ",\nsectionTypeName:" + this.getSectionTypeName() + "\n}";
    }
}

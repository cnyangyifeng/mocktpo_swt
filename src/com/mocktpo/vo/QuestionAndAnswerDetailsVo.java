package com.mocktpo.vo;

public class QuestionAndAnswerDetailsVo {

    private static final long serialVersionUID = 1L;

    private int viewId;
    private int viewType;
    private int sectionType;
    private int listeningGroupId;
    private int questionNumberInSection;
    private String question;
    private String answer;
    private String correctAnswer;

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

    public int getListeningGroupId() {
        return listeningGroupId;
    }

    public void setListeningGroupId(int listeningGroupId) {
        this.listeningGroupId = listeningGroupId;
    }

    public int getQuestionNumberInSection() {
        return questionNumberInSection;
    }

    public void setQuestionNumberInSection(int questionNumberInSection) {
        this.questionNumberInSection = questionNumberInSection;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

package com.lktech.surveytalks.model;

public class QuestionLikeCount {

    private Long questionId;
    private Long likeCount;

    public QuestionLikeCount(Long questionId, Long likeCount) {
        this.questionId = questionId;
        this.likeCount = likeCount;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}


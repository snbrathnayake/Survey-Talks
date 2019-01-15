package com.lktech.surveytalks.payload;

import javax.validation.constraints.NotNull;

public class LikeRequest {

    @NotNull
    private Long questionId; // which question(like) like


    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}

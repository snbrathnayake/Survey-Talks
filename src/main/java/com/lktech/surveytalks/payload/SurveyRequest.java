package com.lktech.surveytalks.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SurveyRequest {

    @NotBlank
    @Size(max = 140)
    private String name;

    @NotBlank
    @Size(max = 140)
    private String category;


    @NotNull
    private boolean isActive;

    @NotNull
    @Valid
    private List<QuestionRequest> questions;

    @NotNull
    @Valid
    private SurveyExpireLength surveyExpireLength;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }

    public SurveyExpireLength getSurveyExpireLength() {
        return surveyExpireLength;
    }

    public void setSurveyExpireLength(SurveyExpireLength surveyExpireLength) {
        this.surveyExpireLength = surveyExpireLength;
    }



}

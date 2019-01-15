package com.lktech.surveytalks.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class QuestionRequest {

    @NotBlank
    @Size(max = 40)
    private String title;

    @NotBlank
    @Size(max = 40)
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

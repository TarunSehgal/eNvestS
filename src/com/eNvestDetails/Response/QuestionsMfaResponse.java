package com.eNvestDetails.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class QuestionsMfaResponse extends MfaResponseDetail {
    
    private Question[] questions;
    
    @JsonProperty("mfa")
    public Question[] getQuestions() {
        return questions;
    }
    
    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }
    
    @Override
    public String getType() {
    	return QUESTIONS;
    }
}
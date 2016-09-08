package com.envest.services.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class Selection {
	private String question;
	private String[] answers;
	
	public String[] getAnswers() {
		return answers;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
}
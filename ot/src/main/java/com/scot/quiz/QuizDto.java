package com.scot.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizDto implements Cloneable {

    private Integer userId;
    private Integer quizId;
    private Integer quizType;
    private String quizText;
    private String inputText;
    private Integer outputCategary;
    private String outputText;

	public Integer getUserId() {
		return userId;
	}

	public Integer getQuizId() {
		return quizId;
	}

	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}

	public Integer getQuizType() {
		return quizType;
	}

	public void setQuizType(Integer quizType) {
		this.quizType = quizType;
	}

	public String getQuizText() {
		return quizText;
	}

	public void setQuizText(String quizText) {
		this.quizText = quizText;
	}

	public String getInputText() {
		return inputText;
	}

	public Integer getOutputCategary() {
		return outputCategary;
	}

	public void setOutputCategary(Integer outputCategary) {
		this.outputCategary = outputCategary;
	}

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

	public Object clone(){
		QuizDto n=null;
		try{
			n=(QuizDto)super.clone();
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		return n;
	}
}
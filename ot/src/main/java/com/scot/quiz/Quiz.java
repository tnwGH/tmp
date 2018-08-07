package com.scot.quiz;

public class Quiz {

	private Integer id;
	private Integer type;
	private String text;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", type=" + type + ", text=" + text + "]";
	}

}
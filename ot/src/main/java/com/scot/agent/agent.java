package com.scot.agent;

public interface agent {
	Boolean validate(String input);
	String compile(String input);
	String execute(String input);
}
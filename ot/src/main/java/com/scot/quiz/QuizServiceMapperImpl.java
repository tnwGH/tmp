package com.scot.quiz;

import com.google.inject.Inject;

public class QuizServiceMapperImpl implements QuizService {

	@Inject private QuizMapper quizMapper;

	public QuizDto getOneDto(Integer id) {
		Quiz quiz = quizMapper.getQuiz(id);
		QuizDto quizDto = new QuizDto();
		quizDto.setQuizId(id);
		quizDto.setQuizType(quiz.getType());
		quizDto.setQuizText(quiz.getText());
		return quizDto;
	};
}
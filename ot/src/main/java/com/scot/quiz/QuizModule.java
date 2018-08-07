package com.scot.quiz;

import com.google.inject.AbstractModule;

public class QuizModule extends AbstractModule {

	@Override
    protected void configure() {
        bind(QuizDto.class);
        bind(QuizService.class).to(QuizServiceMapperImpl.class);

        install(new QuizH2PrepareModule());

        install(new QuizMyBatisModule());
    }

}

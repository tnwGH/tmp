package com.scot.restsvc;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.scot.queue.QueueService;
import com.scot.quiz.QuizDto;
import com.scot.quiz.QuizService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/ot")
@Singleton
public class Service {

	private static final Logger logger = LoggerFactory.getLogger(Service.class);
	@Inject
	private QuizService quizService;
	@Inject
	private QueueService queueService;

    @GET
    public String getNothing() {
    	return "Online Test is running...";
    }

    @GET
    @Path("/quizr")
    public QuizDto getQuizRandomly(@QueryParam("type") String quizType) {
    	// only demo
    	Random r = new Random();
    	Integer id = r.nextInt(5) + 1;

    	logger.info("Getting random quiz..");
    	QuizDto quiz = quizService.getOneDto(id);
    	return quiz;
    }

    @POST
    @Path("/commit")
    public QuizDto exeQuiz(final QuizDto input) throws Exception {
    	QuizDto output = null;
    	if (null != input) {
    		try {
    			output = (QuizDto)input.clone();
    			logger.info("User input is: " + input.getInputText());
    			logger.info("Sending to queue to agents.. blocking for response..");
    			// TODO f
            	output = queueService.request(output);
            	logger.info("Got output done by agent: " + output);
    		} catch (Exception e) {
    			// TODO
    			throw e;
    		}
    	}
    	return output;
    }

}
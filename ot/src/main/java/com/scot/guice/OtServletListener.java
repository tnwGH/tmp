package com.scot.guice;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.scot.queue.QueueModule;
import com.scot.quiz.QuizModule;
import com.scot.restsvc.ServiceModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class OtServletListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {

	  return Guice.createInjector(
    		new ServletModule() {
    			@Override
    			protected void configureServlets() {
    		        // hook Jersey into Guice Servlet
    		        bind(GuiceContainer.class);

    		        // hook Jackson into Jersey as the POJO <-> JSON mapper
    		        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

    		        /*Map<String, String> guiceContainerConfig = new HashMap<String, String>();
    		        guiceContainerConfig.put(k, v);*/
    		        serve("/*").with(GuiceContainer.class);
    			}
    		}, 
    		new ServiceModule(), 
    		new QueueModule(), 
    		new QuizModule()
            //TODO new CacheModule()
    		//TODO new UserModule()
    		);
  }

}
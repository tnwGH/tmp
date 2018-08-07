package com.scot.queue;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Scopes;

public class QueueModule extends AbstractModule {

	@Override
    protected void configure() {
        bind(QueueService.class);
        bind(ConnectionFactory.class).toProvider(AmqCnctFctryProvider.class).in(Scopes.SINGLETON);
    }

    static class AmqCnctFctryProvider implements Provider<ConnectionFactory> {
    	private ConnectionFactory connectionFactory;

    	public AmqCnctFctryProvider() {
    		BrokerService broker;
    		try {
    			broker = BrokerFactory.createBroker(
    					new URI("broker:(tcp://localhost:61616)"));
    			broker.start();
    			connectionFactory = 
    					new ActiveMQConnectionFactory("tcp://localhost:61616");
    			// TODO graceful stop

    		} catch (URISyntaxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    	public ConnectionFactory get() {
    		return connectionFactory;
    	}
    }

}

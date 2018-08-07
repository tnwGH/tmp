package com.scot.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.scot.quiz.QuizDto;

//Let agent box to validate/compile/execute user input code, through message.
@Singleton
public class QueueService {

	private static final Logger logger = LoggerFactory.getLogger(QueueService.class);

	@Inject private ConnectionFactory connectionFactory;

	public QuizDto request(final QuizDto toReturn) {
		String payload = toReturn.getInputText();
		Connection connection = null;
		try {
			// MQ p2p
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("QuizQueue");
			Message msg = session.createTextMessage(payload);

			// Producer
			MessageProducer producer = session.createProducer(queue);
			logger.info("Sending text '" + payload + "'");
			producer.send(msg);

			// Consumer .. TODO only demo
			MessageConsumer consumer = session.createConsumer(queue);
			// TODO f
			//consumer.setMessageListener(new AmqMsgListener("Consumer"));
			logger.info("Listening response..");

			// TODO only demo
			connection.start();
			/*Thread.sleep(1000);
			session.close();*/

			Message message = consumer.receive();
			// TODO only demo
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				if ("foobar".equalsIgnoreCase(textMessage.getText())) {
					toReturn.setOutputCategary(1);
					toReturn.setOutputText("Execution Result is correct: " + textMessage.getText());
				} else {
					toReturn.setOutputCategary(2);
					toReturn.setOutputText("Execution Result is incorrect: xxx from agent...");
				}

			}
			session.close();

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return toReturn;
	}
}
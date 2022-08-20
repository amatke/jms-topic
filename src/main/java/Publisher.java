import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

	public static void main(String[] args) {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");

		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("demo-topic");

			MessageProducer producer = session.createProducer(destination);
			TextMessage textMessage = session.createTextMessage("Message for Topic!");
			producer.send(textMessage);

			System.out.println("Message published to topic");

			session.close();
			connection.close();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}
}

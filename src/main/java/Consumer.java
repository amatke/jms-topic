import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	public static void main(String[] args) {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");

		try {
			Connection connection = connectionFactory.createConnection();
			connection.setClientID("1");
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Topic topic = session.createTopic("demo-topic");

			MessageConsumer consumer = session.createDurableSubscriber(topic, "Subscriber-1"); // DurableSubscriber omogucava da kada je Subscriber offline da kad se aktivira dobijemo poruku
			consumer.setMessageListener(new MessageListener() {

				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;

					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						throw new RuntimeException(e);
					}

				}
			});

		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}
}

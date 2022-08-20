import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	public static void main(String[] args) {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");

		try {
			Connection connection = connectionFactory.createConnection();
			connection.setClientID("1");		//moramo da znamo koji je ID subscriber-a
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Topic topic = session.createTopic("demo-topic"); //ako topic postoji nece ga kreirati ponovi
			// DurableSubscriber nam omogucava da dobijamo poruke i u slucaju kada smo bili offline a zatim se prijavili (dobicemo naknadno poruke od topica tj Publishera)

			MessageConsumer consumer = session.createDurableSubscriber(topic, "Subscriber-1");		//dodedljujemo ime subscriber-u
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

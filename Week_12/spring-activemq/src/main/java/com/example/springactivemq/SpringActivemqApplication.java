package com.example.springactivemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.*;
import java.io.IOException;

@SpringBootApplication
public class SpringActivemqApplication {

    public static void main(String[] args) throws JMSException, IOException {
//        SpringApplication.run(SpringActivemqApplication.class, args);
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61617");
        Connection connection = factory.createConnection();
        connection.start();

        /**
         * transacted:
         *  1、true：支持事务
         *      为true时：paramB的值忽略， acknowledgment mode被jms服务器设置为SESSION_TRANSACTED 。 　
         *  2、false：不支持事务
         *      为false时：paramB的值可为Session.AUTO_ACKNOWLEDGE、Session.CLIENT_ACKNOWLEDGE、DUPS_OK_ACKNOWLEDGE其中一个。
         *
         * acknowledgeMode:
         *          1、Session.AUTO_ACKNOWLEDGE：为自动确认，客户端发送和接收消息不需要做额外的工作。
         *          2、Session.CLIENT_ACKNOWLEDGE：为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。
         *          3、DUPS_OK_ACKNOWLEDGE：允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
         *          4、SESSION_TRANSACTED
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        queueMq(session);

        topicMq(session);

        session.close();
        connection.close();
    }

    private static void topicMq(Session session) throws JMSException, IOException {
        Topic topic = session.createTopic("test.topic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("topic customer :" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("topic producer send 1.");
        producer.send(textMessage);
        System.out.println("input :");
        System.in.read();
        consumer.close();
        producer.close();
    }

    private static void queueMq(Session session) throws JMSException, IOException {
        Queue queue = session.createQueue("test.queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("queue customer :" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage("queue producer send 1");
        producer.send(textMessage);
        System.out.println("input :");
        System.in.read();
        consumer.close();
        producer.close();
    }

}

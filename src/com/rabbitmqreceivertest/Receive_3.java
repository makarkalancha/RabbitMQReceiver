/*
 * Software property of Acquisio. Copyright 2003-2017.
 */
package com.rabbitmqreceivertest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Macar Calancea (mcalancea@acquisio.com)
 */
public class Receive_3 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("queueName: " + queueName);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.print(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                }finally {
                    System.out.println(" [x] Done");
//                        channel.basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };

        channel.basicConsume(queueName, true, consumer);

    }

    private static void doWork(String task){
        try {
            for (int i = 0; i < 5; i++) {
                System.out.print(".");
                Thread.sleep(TimeUnit.SECONDS.toMillis(1L));
            }
            System.out.println();
        }catch (InterruptedException e){
            System.out.println("Exception!!!");
            System.out.println(e);
        }
    }

}

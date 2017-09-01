package com.rabbitmqreceivertest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by Makar Kalancha
 * Date: 22 Nov 2016
 * Time: 15:33
 */
public class Receive {
    private final static String QUEUE_NAME = "hello";
    private static Channel channel = null;

    public static void main(String[] args) throws Exception{
        Connection connection = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();

            connection = factory.newConnection();
            channel = connection.createChannel();

            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(TimeUnit.MILLISECONDS.toMinutes(3600000L));
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C and then Enter");

            channel.basicQos(1);//accepts only one unack-ed message at a time
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
            boolean autoAck = true;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                int key = bufferedReader.read();
                if(key == 10){
                    break;
                }
            }

            channel.waitForConfirmsOrDie(3600000L);
        }finally {
            if(channel != null) channel.close();
            if(connection != null) connection.close();
        }
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

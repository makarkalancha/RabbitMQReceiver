/*
 * Software property of Acquisio. Copyright 2003-2017.
 */
package com.rabbitmqreceivertest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author Macar Calancea (mcalancea@acquisio.com)
 *         http://pika.readthedocs.io/en/0.10.0/examples/asynchronous_consumer_example.html
 */
public class Receiver_Asynchronous {

    private static final String EXCHANGE = "message";
    private static final String EXCHANGE_TYPE = "topic";
    private static final String QUEUE = "text";
    private static final String ROUTING_KEY = "example.text";

    private final Connection _connection = null;
    private final Channel _channel = null;
    private final boolean _closing = false;
    private final String _consumer_tag = null;
    private final String _url = "localhost";

    private void connect(){

    }

//    private void on_connection_open(){
//        System.out.println("Connection opened");
//        add_on_connection_close_callback();
//        open_channel();
//    }
//
//    private void add_on_connection_close_callback(){
//        System.out.println("Adding connection close callback");
//        _connection.add_on_close_callback(on_connection_closed);
//
//    }
//
//    private void on_connection_closed(Connection connection, String reply_code, String reply_text){
//        _channel = null;
//        if(_closing) {
//            _connection.ioloop.stop()
//        }else:
//        LOGGER.warning('Connection closed, reopening in 5 seconds: (%s) %s',
//                reply_code, reply_text)
//        self._connection.add_timeout(5, self.reconnect)
//
//    }

    public static void main(String[] args) {

    }
}

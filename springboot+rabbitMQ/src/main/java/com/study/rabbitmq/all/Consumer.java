package com.study.rabbitmq.all;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @projectName: springbootRabbitMQ
 * @package: com.study.rabbitmq.simple
 * @className: Consumer
 * @author: tang wen jun
 * @description: 消息消费者
 * @date: 2021年05月27日 23:35
 * @version: 1.0
 */
public class Consumer {

    private static Runnable runnable = new Runnable() {
        public void run() {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            connectionFactory.setVirtualHost("/");
            Connection connection = null;
            Channel channel = null;
            // 获取队列名称
            final String queueName = Thread.currentThread().getName();
            try {
                connection = connectionFactory.newConnection("我是消费者");
                channel = connection.createChannel();
                /**
                 * @param1 队列名字
                 * @param2 是否要持久化
                 * @param3 排他性， 是否是独占独立队列
                 * @param4 最后一个消费者消费后是否自动删除
                 * @param5 携带的其他参数
                 */
                channel.basicConsume(queueName, true, new DeliverCallback() {
                    public void handle(String consumerTag, Delivery message) throws IOException {
                        System.out.println(queueName+"我收到的消息是:==>" + new String(message.getBody(), "UTF-8"));
                    }
                }, new CancelCallback() {
                    public void handle(String consumerTag) throws IOException {
                        System.out.println("消息接受失败");
                    }
                });

                // 暂停程序 不在往下执行
//             System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null && channel.isOpen()) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null && connection.isOpen()) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public static void main(String[] args) {
        new Thread(runnable, "queue5").start();
        new Thread(runnable, "queue6").start();
        new Thread(runnable, "queue7").start();

    }

}

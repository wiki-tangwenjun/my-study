package com.study.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @projectName: springbootRabbitMQ
 * @package: com.study.rabbitmq.simple
 * @className: Consumer
 * @author: tang wen jun
 * @description: 消息消费者
 * @date: 2021年05月24日 23:04
 * @version: 1.0
 */
public class Consumer {
    private static final String QUEUENAME = "queu1";

    public static void main(String[] args) {
        /**
         * rabbit是基于tcp/ip协议之上的amqp协议
         *
         * 1：创建连接工程
         * 2：创建连接Connection
         * 3：通过连接获取通道Channel
         * 4：通过创建交换机，声明队列，绑定关系，路由key, 发送消息，和接收消息
         * 5：准备消息内容
         * 6：发送消息给queue
         * 7：关闭连接
         * 8：关闭通道
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection("我是生产者");
            channel = connection.createChannel();
            /**
             * @param1 队列名字
             * @param2 是否要持久化
             * @param3 排他性， 是否是独占独立队列
             * @param4 最后一个消费者消费后是否自动删除
             * @param5 携带的其他参数
             */
             channel.basicConsume(QUEUENAME, true, new DeliverCallback() {
                 public void handle(String consumerTag, Delivery message) throws IOException {
                     System.out.println("我收到的消息是:==>" + new String(message.getBody(), "UTF-8"));
                 }
             }, new CancelCallback() {
                 public void handle(String consumerTag) throws IOException {
                     System.out.println("消息接受失败");
                 }
             });
            System.out.println("开始接受消息");
//             System.in.read();
            System.out.println("消息发送成功");
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

}

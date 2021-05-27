package com.study.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @projectName: springbootRabbitMQ
 * @package: com.study.rabbitmq.simple
 * @className: Producer
 * @author: tang wen jun
 * @description: 消息生产者
 * @date: 2021年05月27日 23:30
 * @version: 1.0
 */
public class Producer {
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
            channel.queueDeclare(QUEUENAME, false, false, false, null);

            // 消息内容
            String message = "hello rabbitMQ";

            // 交换机名称
            String exchangeName = "fanout-exchange";

            // 路由key
            String routingKey = "";

            // 交换机类型
            String type = "fanout";

            // param1 交换机  param2 队列、路由队列 param3 消息是否持久化  param4 消息内容
            // 虽然没有u才能在交换机 但是会默认存在一个交换机
            channel.basicPublish(exchangeName, routingKey, null, message.getBytes());

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

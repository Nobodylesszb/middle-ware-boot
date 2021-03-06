package com.bo.rabbitmq.consumer;

import com.bo.rabbitmq.entity.Client;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("rabbitJsonConsumer")
@RabbitListener(queues = RabbitJsonConsumer.JSON_QUEUE)
public class RabbitJsonConsumer {
    public static final String JSON_QUEUE = "erduo_json";

    @RabbitHandler
    public void onMessage(Client client, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        System.out.println("Message content : " + client);
        System.out.println("Message headers : " + headers);
        channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
        System.out.println("消息已确认");
    }

}
package com.bo.rabbitmq.consumer;

import com.bo.rabbitmq.constants.StringConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rabbitConsumer")
public class RabbitConsumer {

    @RabbitListener(queues = StringConstants.QUEUE_NAME)
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("Message content : " + message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        System.out.println("消息已确认");
    }

}

package org.yangxin.rabbitmq.rabbitmqspringbootproducer.rabbitmqspringbootproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yangxin
 * 1/12/21 12:09 AM
 */
@Component
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Object message, Map<String, Object> propertiesMap) {
        MessageHeaders messageHeaders = new MessageHeaders(propertiesMap);
        Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.convertAndSend("exchange-1", "springboot.rabbitmq.producer", msg);
    }
}

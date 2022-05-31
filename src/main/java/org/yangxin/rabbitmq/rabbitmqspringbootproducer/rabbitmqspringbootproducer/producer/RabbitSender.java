package org.yangxin.rabbitmq.rabbitmqspringbootproducer.rabbitmqspringbootproducer.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.yangxin.rabbitmq.rabbitmqspringbootproducer.rabbitmqspringbootproducer.entity.Order;

import java.util.Map;

/**
 * @author yangxin
 * 1/12/21 12:09 AM
 */
@SuppressWarnings({"AlibabaRemoveCommentedCode"})
@Component
@Slf4j
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 消息入队列的回调
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        log.info("correlationData: [{}], ack: [{}]", correlationData, ack);

        // 入队失败！
        if (!ack) {
            log.error("异常处理……");
        }
    };

    /**
     * 消息不可达的回调
     */
    private final RabbitTemplate.ReturnsCallback returnsCallback = (returned) -> log.info("returned: [{}]", returned);

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Object message, Map<String, Object> propertiesMap) {
        MessageHeaders messageHeaders = new MessageHeaders(propertiesMap);
        Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);
        // id+时间戳，全局唯一
        CorrelationData correlationData = new CorrelationData("1234567890");

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
//        rabbitTemplate.convertAndSend("exchange-1", "springboot.rabbitmq.producer", msg, correlationData);
        rabbitTemplate.convertAndSend("exchange-1", "springboot", msg, correlationData);
    }

    public void sendOrder(Order order) {
        // id+时间戳，全局唯一
        CorrelationData correlationData = new CorrelationData("0987654321");

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
        rabbitTemplate.convertAndSend("exchange-2",
                "springboot.rabbitmq.producer", order, correlationData);
    }
}

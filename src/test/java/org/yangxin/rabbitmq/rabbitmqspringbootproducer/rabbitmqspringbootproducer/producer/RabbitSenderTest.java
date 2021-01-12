package org.yangxin.rabbitmq.rabbitmqspringbootproducer.rabbitmqspringbootproducer.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yangxin.rabbitmq.rabbitmqspringbootproducer.rabbitmqspringbootproducer.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxin
 * 1/12/21 12:38 AM
 */
@SpringBootTest
class RabbitSenderTest {

    @Autowired
    private RabbitSender rabbitSender;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    void send() {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("number", "123456");
        propertiesMap.put("send_time", SIMPLE_DATE_FORMAT.format(new Date()));

        rabbitSender.send("Hello RabbitMQ For Spring Boot!", propertiesMap);
    }

    @Test
    void sendOrder() {
        Order order = new Order("001", "第一个订单");
        rabbitSender.sendOrder(order);
    }
}
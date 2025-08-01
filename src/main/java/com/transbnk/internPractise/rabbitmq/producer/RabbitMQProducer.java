package com.transbnk.internPractise.rabbitmq.producer;

import com.transbnk.internPractise.rabbitmq.dto.RabbitMQMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// This service annotation will create it bean
// & register it in the Spring IoC container
@Service
@Slf4j
public class RabbitMQProducer {

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(RabbitMQMessageDto rabbitMQMessageDto) {
        log.info("Message sent -> {}", rabbitMQMessageDto.toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, new RabbitMQMessageDto(rabbitMQMessageDto.getMessage()));
    }
}

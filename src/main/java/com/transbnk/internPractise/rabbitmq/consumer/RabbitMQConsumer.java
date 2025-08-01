package com.transbnk.internPractise.rabbitmq.consumer;

import com.transbnk.internPractise.rabbitmq.dto.RabbitMQMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void consume(RabbitMQMessageDto rabbitMQMessageDto) {
        log.info("Message Received -> {} ",rabbitMQMessageDto.toString() );

    }
}

package com.transbnk.internPractise.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.name}")
    private String queue;

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.routing.key}")
    private String routingKey;


    //Spring Bean to configure RabbitMQ Queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    // Spring Bean to configure the RabbitMQ Exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // Spring Bean to configure the Binding of Queue & Exchange
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
    // Spring Boot will configure the above 3 beans automatically.

}
package com.transbnk.internPractise.rabbitmq.controller;


import com.transbnk.internPractise.rabbitmq.dto.RabbitMQMessageDto;
import com.transbnk.internPractise.rabbitmq.producer.RabbitMQProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RabbitMQMessageController {

    private final RabbitMQProducer producer;

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody RabbitMQMessageDto rabbitMQDto){
       try {
           producer.sendMessage(rabbitMQDto);
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body("Message sent Successfully.");
       } catch (Exception e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("An error has occurred " + e.getMessage());
       }
    }
}

package com.transbnk.internPractise.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendMail("shreyas.dankhade-i@transbnk.co.in",
                "Testing chalri hy",
                "Hi, email aaya tho samj jana ki mail service chlra hai.");
    }
}

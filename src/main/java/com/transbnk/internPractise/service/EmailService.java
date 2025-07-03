package com.transbnk.internPractise.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    private JavaMailSender javaMailSender;

        public void sendMail(String to, String subject, String body){
            try{
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(to);
                mail.setSubject(subject);
                mail.setText(body);
                javaMailSender.send(mail);
            } catch (Exception e) {
                log.error("Exception while sendEmail ", e);
            }
        }
}

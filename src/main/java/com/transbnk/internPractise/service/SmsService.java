package com.transbnk.internPractise.service;

import com.transbnk.internPractise.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsService {
    private final TwilioConfig twilioConfig;

    @PostConstruct
    public void init(){
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
    }

    public String sendSms(String to, String body){
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfig.getFromNumber()),
                body
        ).create();
        System.out.println("From: " + twilioConfig.getFromNumber());


        return  message.getSid();
    }
}

package com.transbnk.internPractise.controller;

import com.transbnk.internPractise.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestParam("to") String to, @RequestParam("message") String message){
        try {
            smsService.sendSms(to,message);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("The SMS has been sent to the given Phone Number: "+ to);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Error has occured" + e.getMessage());
        }
    }
}

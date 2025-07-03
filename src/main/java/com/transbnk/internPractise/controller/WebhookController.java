package com.transbnk.internPractise.controller;

import com.transbnk.internPractise.entity.Payment;
import com.transbnk.internPractise.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
@Slf4j
@AllArgsConstructor
public class WebhookController {

    private PaymentService paymentService;


    @PostMapping("/payint")
    public ResponseEntity<?> payInt(@PathVariable Long id,@PathVariable String status){
        try{
            paymentService.payInt(id,status);
            return ResponseEntity.ok("Payment Intialized sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to initialize Payment.");
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> webhookRec(@RequestBody Payment payment, @RequestHeader("X-Auth-Token") String token){
        if (!"sec123".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        log.info("Webhook received:");
        log.info("Payment ID: {}", payment.getId());
        log.info("Status: {}", payment.getStatus());

        return ResponseEntity.ok("Webhook processed successfully");
    }

}

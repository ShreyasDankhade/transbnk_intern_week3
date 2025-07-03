package com.transbnk.internPractise.service;

import com.transbnk.internPractise.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final RestTemplate restTemplate;

    private final String WEBHOOK_URL = "http://localhost:8082/payment/receive";

    public void payInt(Long id, String status){
        try{
            Payment payment = new Payment();
            payment.setId(id);
            payment.setStatus(status);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Auth-Token", "sec123");

            HttpEntity<Payment> request = new HttpEntity<>(payment,headers);

            restTemplate.postForEntity(WEBHOOK_URL, request, String.class);
            log.info("Webhook Sent Successfully for ID: {}", id);
        } catch (Exception e) {
            log.error("Failed to send webhook for Payment ID {}: {}", id, e.getMessage());}
    }
    
}

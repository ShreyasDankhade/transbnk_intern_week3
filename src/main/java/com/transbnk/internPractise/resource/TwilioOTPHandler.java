package com.transbnk.internPractise.resource;

import com.transbnk.internPractise.dto.TwilioOTPRequestDto;
import com.transbnk.internPractise.service.TwilioOTPService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TwilioOTPHandler {

    private final TwilioOTPService twilioOTPService;

    public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TwilioOTPRequestDto.class)
                .flatMap(twilioOTPService::sendOTP)
                .flatMap(responseDto -> ServerResponse.status(HttpStatus.OK).bodyValue(responseDto))
                .onErrorResume(e ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue("Failed to send OTP: " + e.getMessage()));
    }

    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TwilioOTPRequestDto.class)
                .flatMap(dto -> twilioOTPService.validateOTP(dto.getOtp(), dto.getUsername()))
                .flatMap(successMsg -> ServerResponse.ok().bodyValue(successMsg))
                .onErrorResume(e ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST)
                                .bodyValue("OTP validation failed: " + e.getMessage()));
    }
}
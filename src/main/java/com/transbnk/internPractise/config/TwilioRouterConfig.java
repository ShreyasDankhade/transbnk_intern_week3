package com.transbnk.internPractise.config;

import com.transbnk.internPractise.resource.TwilioOTPHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@AllArgsConstructor
public class TwilioRouterConfig {
    private final TwilioOTPHandler twilioOTPHandler;

    @Bean
    public RouterFunction<ServerResponse> handleSMS(){
        return RouterFunctions.route()
                .POST("/api/otp/send", twilioOTPHandler::sendOTP)
                .POST("/api/otp/validate", twilioOTPHandler::validateOTP)
                .GET("/test", request -> ServerResponse.ok().bodyValue("Router working"))
                .build();
    }
}

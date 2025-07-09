package com.transbnk.internPractise.service;

import com.transbnk.internPractise.config.TwilioConfig;
import com.transbnk.internPractise.dto.OtpStatus;
import com.transbnk.internPractise.dto.TwilioOTPRequestDto;
import com.transbnk.internPractise.dto.TwilioOTPResponseDto;
import com.transbnk.internPractise.entity.Otp;
import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.OtpRepo;
import com.transbnk.internPractise.repository.UserRepo;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class TwilioOTPService {

    private final TwilioConfig twilioConfig;
    private final OtpRepo otpRepo;
    private final UserRepo userRepo;

    public Mono<TwilioOTPResponseDto> sendOTP(TwilioOTPRequestDto requestDto) {
        TwilioOTPResponseDto responseDto;

        try {
            User user = userRepo.findByPhoneNumber(requestDto.getToNumber())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with phone number"));

            PhoneNumber to = new PhoneNumber(user.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getFromNumber());

            String otp = generateOTP();
            String otpMessage = "Dear Customer, Your OTP is - " + otp +
                    " Use this Code to complete the process. Do not Share this code.";

            Message.creator(to, from, otpMessage).create();

            // Save OTP
            Otp otpEntity = Otp.builder()
                    .otp(otp)
                    .createdAt(LocalDateTime.now())
                    .used(false)
                    .user(user)
                    .build();

            otpRepo.save(otpEntity);

            responseDto = new TwilioOTPResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            responseDto = new TwilioOTPResponseDto(OtpStatus.FAILED, e.getMessage());
        }

        return Mono.just(responseDto);
    }


    public Mono<String> validateOTP(String inputOtp, String username) {
        return userRepo.findByPhoneNumber(username)
                .map(user -> {
                    Optional<Otp> otpOpt = otpRepo.findTopByUserOrderByCreatedAtDesc(user);
                    if (otpOpt.isEmpty()) {
                        throw new IllegalArgumentException("No OTP found for user.");
                    }

                    Otp otp = otpOpt.get();

                    if (otp.isUsed()) {
                        throw new IllegalArgumentException("OTP already used.");
                    }

                    if (!otp.getOtp().equals(inputOtp)) {
                        throw new IllegalArgumentException("Invalid OTP.");
                    }

                    otp.setUsed(true);
                    otpRepo.save(otp);
                    return "Valid OTP for transaction.";
                })
                .map(Mono::just)
                .orElse(Mono.error(new IllegalArgumentException("User not found")));
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }


}

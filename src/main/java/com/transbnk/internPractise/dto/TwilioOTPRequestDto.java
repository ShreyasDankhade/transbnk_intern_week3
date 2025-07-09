package com.transbnk.internPractise.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwilioOTPRequestDto {
    private String toNumber;
    private String otp;
    private String username;
}

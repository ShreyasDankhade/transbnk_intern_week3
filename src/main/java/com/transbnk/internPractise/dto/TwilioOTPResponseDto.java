package com.transbnk.internPractise.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwilioOTPResponseDto {
    private OtpStatus status;
    private String message;
}

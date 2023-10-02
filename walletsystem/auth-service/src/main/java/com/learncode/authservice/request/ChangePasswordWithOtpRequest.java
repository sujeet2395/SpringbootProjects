package com.learncode.authservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordWithOtpRequest {
    private String username;
    private String otp;
    private String newPassword;
}

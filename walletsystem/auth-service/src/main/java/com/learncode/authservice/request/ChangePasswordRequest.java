package com.learncode.authservice.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}

package com.learnoauthtwoo.springoauth.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequest {
    private String  username;
    private String password;
}
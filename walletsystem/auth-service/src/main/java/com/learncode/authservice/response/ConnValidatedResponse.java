package com.learncode.authservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnValidatedResponse {
	private int httpStatus;
	private boolean isAuthenticated;
	private String methodType;
	private String username;
}

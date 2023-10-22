package com.learncode.gatewayservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConnValidatedResponse {
	private int httpStatus;
	private boolean isAuthenticated;
	private String methodType;
	private String username;
}

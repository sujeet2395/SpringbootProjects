package com.learncode.authservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private int httpStatus;
	private String error;
	private String message;
	private String path;
}

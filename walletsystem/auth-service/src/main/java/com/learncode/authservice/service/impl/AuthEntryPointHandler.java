package com.learncode.authservice.service.impl;

import java.io.IOException;

import com.learncode.authservice.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthEntryPointHandler.class);

  @Autowired
  private ObjectMapper objectMapper;
  
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
	  LOGGER.error("Unauthorized error: {}", authException.getMessage());

	  response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	  ErrorResponse errorResponse = new ErrorResponse();
	  errorResponse.setHttpStatus(HttpServletResponse.SC_UNAUTHORIZED);
	  errorResponse.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
	  errorResponse.setMessage("You are not authenticated.");//authException.getMessage()
	  errorResponse.setPath(request.getServletPath());

	  objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
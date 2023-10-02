package com.learncode.authservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learncode.authservice.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        LOGGER.error("Forbidden error: {}", exception.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpServletResponse.SC_FORBIDDEN);
        errorResponse.setError("Forbidden");
        errorResponse.setMessage("Access Denied: You are not authorized.");//authException.getMessage()
        errorResponse.setPath(request.getServletPath());

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

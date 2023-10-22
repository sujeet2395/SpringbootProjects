package com.learncode.authservice.controller;

import com.learncode.authservice.request.*;
import com.learncode.authservice.response.ConnValidatedResponse;
import com.learncode.authservice.service.interfaces.EmailService;
import com.learncode.authservice.service.interfaces.UserService;
import org.apache.hc.core5.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/user/user-profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/admin-profile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/user/signup")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody SignupRequest signupRequest) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data",userService.createUser(signupRequest));
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data",userService.getUserById(userId));
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/user/by-username/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data",userService.getUserByUsername(username));
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data",userService.getAllUsers());
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data",userService.updateUser(userId, userUpdateRequest));
            response.put("success",true);
        }catch (AccessDeniedException e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try{
            userService.deleteUser(userId);
            response.put("data","User deleted successfully.");
            response.put("success",true);
        }catch (AccessDeniedException e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Map<String, Object> response = new HashMap<>();
        try{
            userService.changePassword(changePasswordRequest);
            response.put("data","Password changed successfully.");
            response.put("success",true);
        }catch (AccessDeniedException e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/user/change-password-with-otp")
    public ResponseEntity<Map<String, Object>> changePasswordWithOtp(@RequestBody ChangePasswordWithOtpRequest changePasswordWithOtpRequest) {
        Map<String, Object> response = new HashMap<>();
        try{
            userService.changePasswordWithOtp(changePasswordWithOtpRequest);
            response.put("data","Password reset successfully.");
            response.put("success",true);
        }catch (AccessDeniedException e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        Map<String, Object> response = new HashMap<>();
        try{
            userService.resetPassword(resetPasswordRequest);
            response.put("data","An email containing OTP sent to your email. Please follow further instruction.");
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/send-mail")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody EmailMessage emailMessage) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("Sending emailMessage body: {}", emailMessage);
            emailService.sendMail(emailMessage);
            response.put("data","Email sent successfully.");
            response.put("success",true);
        } catch (Exception ex) {
            log.info("Exception Occurred :- " + ex.getMessage());
            response.put("success",false);
            response.put("exception", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/validate-token")
    public ResponseEntity<ConnValidatedResponse> validateToken(Authentication authentication)
    {
        ConnValidatedResponse connValidatedResponse = new ConnValidatedResponse();
        connValidatedResponse.setHttpStatus(HttpStatus.OK.value());
        connValidatedResponse.setAuthenticated(true);
        connValidatedResponse.setMethodType(Method.GET.name());
        connValidatedResponse.setUsername(authentication.getName());
        return new ResponseEntity<ConnValidatedResponse>(connValidatedResponse,HttpStatus.OK);
    }
}

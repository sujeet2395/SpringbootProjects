package com.learncode.authservice.controller;

import com.learncode.authservice.request.*;
import com.learncode.authservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
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

    @GetMapping("/user/{username}")
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
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
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

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferFunds(
            @RequestParam Long senderUserId,
            @RequestParam Long receiverUserId,
            @RequestParam double amount) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data","Funds transferred successfully");
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}

package com.learncode.authservice.controller;

import com.learncode.authservice.entity.User;
import com.learncode.authservice.repository.UserRepository;
import com.learncode.authservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("data",userService.createUser(user));
            response.put("success",true);
        }catch (Exception e)
        {
            response.put("exception",e.getMessage());
            response.put("success",false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferFunds(
            @RequestParam Long senderUserId,
            @RequestParam Long receiverUserId,
            @RequestParam double amount) {
        // Implement fund transfer logic here
        // Update sender and receiver wallets
        // Return success or error message
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

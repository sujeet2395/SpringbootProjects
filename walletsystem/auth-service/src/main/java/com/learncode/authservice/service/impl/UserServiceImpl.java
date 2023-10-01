package com.learncode.authservice.service.impl;

import com.learncode.authservice.entity.User;
import com.learncode.authservice.exception.ResourceNotFoundException;
import com.learncode.authservice.repository.UserRepository;
import com.learncode.authservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        // Implement user creation logic, e.g., validation, hashing passwords, etc.
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        // Implement user update logic
        // Ensure that the user with the given userId exists
        // Update user fields accordingly
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        // Implement user deletion logic
        if(userRepository.existsById(userId))
            userRepository.deleteById(userId);
        else
            throw new ResourceNotFoundException("User not found by userId: "+userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found by username: "+username));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

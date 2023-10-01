package com.learncode.authservice.service.interfaces;

import com.learncode.authservice.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}

package com.learncode.authservice.service.impl;

import com.learncode.authservice.entity.Role;
import com.learncode.authservice.entity.User;
import com.learncode.authservice.exception.ResourceNotFoundException;
import com.learncode.authservice.repository.RoleRepository;
import com.learncode.authservice.repository.UserRepository;
import com.learncode.authservice.request.SignupRequest;
import com.learncode.authservice.request.UserUpdateRequest;
import com.learncode.authservice.response.UserResponse;
import com.learncode.authservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${auth.user.admin.create}")
    private boolean isCreateAdmin;

    @Override
    public UserResponse createUser(SignupRequest signupRequest) {
        checkEmailExist(signupRequest.getEmail());
        checkUsernameExist(signupRequest.getUsername());
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        Role role = isCreateAdmin? roleRepository.findByRole("ROLE_ADMIN").orElse(Role.builder().role("ROLE_ADMIN").build()) :
                roleRepository.findByRole("ROLE_USER").orElse(Role.builder().role("ROLE_USER").build());
        role.setUsers(List.of(user));
        List<Role> roles = List.of(role);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return mapUserToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User userInstanceFromDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if(Objects.nonNull(userUpdateRequest.getUsername()) && !userUpdateRequest.getUsername().isEmpty() && !userInstanceFromDB.getUsername().equalsIgnoreCase(userUpdateRequest.getUsername()))
        {
            checkUsernameExist(userUpdateRequest.getUsername());
            userInstanceFromDB.setUsername(userUpdateRequest.getUsername());
        }
        if(Objects.nonNull(userUpdateRequest.getEmail()) && !userUpdateRequest.getEmail().isEmpty() && !userInstanceFromDB.getEmail().equalsIgnoreCase(userUpdateRequest.getEmail()))
        {
            checkEmailExist(userUpdateRequest.getEmail());
            userInstanceFromDB.setEmail(userUpdateRequest.getEmail());
        }
        User savedUser = userRepository.save(userInstanceFromDB);
        return mapUserToUserResponse(savedUser);
    }

    private void checkEmailExist(String email) {
        if(userRepository.existsByEmail(email))
            throw new RuntimeException("Email is not available.");
    }

    private void checkUsernameExist(String username) {
        if(userRepository.existsByUsername(username))
            throw new RuntimeException("Username is not available.");
    }

    @Override
    public void deleteUser(Long userId) {
        if(userRepository.existsById(userId))
            userRepository.deleteById(userId);
        else
            throw new ResourceNotFoundException("User not found by userId: "+userId);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found by username: "+userId));
        return mapUserToUserResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found by username: "+username));
        return mapUserToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
         return userRepository.findAll().stream().map(this::mapUserToUserResponse).toList();
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Role::getRole).toList()).build();
    }
}

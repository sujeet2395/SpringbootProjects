package com.learncode.authservice.service.impl;

import com.learncode.authservice.apiconstant.Constant;
import com.learncode.authservice.entity.PasswordResetOtp;
import com.learncode.authservice.entity.Role;
import com.learncode.authservice.entity.User;
import com.learncode.authservice.exception.ResourceNotFoundException;
import com.learncode.authservice.repository.PasswordResetOtpRepository;
import com.learncode.authservice.repository.RoleRepository;
import com.learncode.authservice.repository.UserRepository;
import com.learncode.authservice.request.*;
import com.learncode.authservice.response.UserResponse;
import com.learncode.authservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetOtpRepository passwordResetOtpRepository;

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
        UserDetails authUserDetails = getAuthenticatedUser();
        if(Objects.isNull(authUserDetails) || (authUserDetails.getAuthorities().contains(Role.builder().role("ROLE_USER").build()) && !(authUserDetails.getUsername().equals(userInstanceFromDB.getUsername())))) {
            throw new AccessDeniedException("Forbidden: Access Denied.");
        }
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
        if(userRepository.existsByEmailIgnoreCase(email))
            throw new RuntimeException("Email is not available.");
    }

    private void checkUsernameExist(String username) {
        if(userRepository.existsByUsernameIgnoreCase(username))
            throw new RuntimeException("Username is not available.");
    }

    @Override
    public void deleteUser(Long userId) {
        User userInstanceFromDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        UserDetails authUserDetails = getAuthenticatedUser();
        if(Objects.isNull(authUserDetails) || (authUserDetails.getAuthorities().contains(Role.builder().role("ROLE_USER").build()) && !(authUserDetails.getUsername().equals(userInstanceFromDB.getUsername())))) {
            throw new AccessDeniedException("Forbidden: Access Denied.");
        }
        userRepository.deleteById(userId);
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

    @Override
    public void changePassword(ChangePasswordRequest passwordChangeRequest) {
        User userInstanceFromDB = userRepository.findByUsername(passwordChangeRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + passwordChangeRequest.getUsername()));
        UserDetails authUserDetails = getAuthenticatedUser();
        if(Objects.isNull(authUserDetails) || !(authUserDetails.getUsername().equals(userInstanceFromDB.getUsername()))) {
            throw new AccessDeniedException("Forbidden: Access Denied.");
        }
        if(passwordEncoder.matches(passwordChangeRequest.getOldPassword(), userInstanceFromDB.getPassword())) {
            userInstanceFromDB.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            userRepository.save(userInstanceFromDB);
        }else{
            throw new RuntimeException("Invalid credential.");
        }
    }

    @Override
    public void changePasswordWithOtp(ChangePasswordWithOtpRequest changePasswordWithOtpRequest) {
        PasswordResetOtp passwordResetOtp = passwordResetOtpRepository.findByOtp(changePasswordWithOtpRequest.getOtp()).orElseThrow(() -> new ResourceNotFoundException("OTP is Invalid."));
        if(passwordResetOtp.isExpired())
            throw new RuntimeException("The OTP has expired. Please try again by resetting your password.");
        User userInstanceFromDB = userRepository.findByUsername(changePasswordWithOtpRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + changePasswordWithOtpRequest.getUsername()));
        if(!userInstanceFromDB.getId().equals(passwordResetOtp.getUser().getId())) {
            throw new AccessDeniedException("Forbidden: Access Denied.");
        }
        userInstanceFromDB.setPassword(passwordEncoder.encode(changePasswordWithOtpRequest.getNewPassword()));
        userRepository.save(userInstanceFromDB);
        passwordResetOtpRepository.deleteById(passwordResetOtp.getId());
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String otp = generateRandomOtp();
        User user = userRepository.findByEmailIgnoreCase(resetPasswordRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+resetPasswordRequest.getEmail()));
        PasswordResetOtp passwordResetOtp = new PasswordResetOtp(LocalDateTime.now().plusMinutes(10), otp,  user);
        passwordResetOtpRepository.save(passwordResetOtp);
    }

    private String generateRandomOtp() {
        int min = (int) Math.pow(10, Constant.OTP_LENGTH - 1);
        int max = (int) Math.pow(10, Constant.OTP_LENGTH) - 1;
        int randomInt = new Random().nextInt(max - min + 1) + min;
        return String.format("%0" + Constant.OTP_LENGTH + "d", randomInt);
    }
    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Role::getRole).toList()).build();
    }

    private UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}

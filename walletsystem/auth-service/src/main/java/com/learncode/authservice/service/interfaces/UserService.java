package com.learncode.authservice.service.interfaces;

import com.learncode.authservice.request.*;
import com.learncode.authservice.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(SignupRequest signupRequest);

    UserResponse updateUser(Long userId, UserUpdateRequest user);

    void deleteUser(Long userId);

    UserResponse getUserById(Long userId);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    void changePassword(ChangePasswordRequest passwordChangeRequest);

    void changePasswordWithOtp(ChangePasswordWithOtpRequest changePasswordWithOtpRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}

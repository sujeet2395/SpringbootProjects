package com.learn.springauthsecurity.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learn.springauthsecurity.entity.Role;
import com.learn.springauthsecurity.entity.User;
import com.learn.springauthsecurity.model.SignupRequest;
import com.learn.springauthsecurity.repository.RoleRepository;
import com.learn.springauthsecurity.repository.UserRepository;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username is not found"));
        return user;
    }

    public void signUp(SignupRequest signupRequest){
        Role role = roleRepository.findByRole("user").orElse(
           Role.builder().role("user").build());
        User user = User.builder().username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .roles(Arrays.asList(role))
                .build();
        userRepository.save(user);
    }
}
package com.learnoauthtwoo.springoauth.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learnoauthtwoo.springoauth.entities.Role;
import com.learnoauthtwoo.springoauth.entities.User;
import com.learnoauthtwoo.springoauth.model.SignupRequest;
import com.learnoauthtwoo.springoauth.repository.RoleRepository;
import com.learnoauthtwoo.springoauth.repository.UserRepository;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
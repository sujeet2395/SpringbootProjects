package com.learnoauthtwoo.springoauth.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learnoauthtwoo.springoauth.manager.UserDetailsServiceImpl;
import com.learnoauthtwoo.springoauth.model.SignupRequest;

@RestController
public class Resource {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/home")
    @PreAuthorize(value = "isAuthenticated()")
    public String home(Authentication authentication){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
        = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials());
        return "Hello "+ usernamePasswordAuthenticationToken.getName()+". You are at the home page";
    }

    @GetMapping("/guest")
    @PreAuthorize(value = "permitAll()")
    public String guest(){
        return "You are at the guest page";
    }

    @GetMapping("/admin")
    @PreAuthorize(value = "hasAnyAuthority('admin')")
    public String admin(){
        return "You are at the admin page";
    }

    @PostMapping("/signup")
    @PreAuthorize(value = "permitAll()")
    public void signUp(@RequestBody SignupRequest signupRequest){
        userDetailsService.signUp(signupRequest);
    }
}
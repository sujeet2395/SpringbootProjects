package com.learn.springauthsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springauthsecurity.model.SignupRequest;
import com.learn.springauthsecurity.service.UserDetailsServiceImpl;

@RestController
public class Resource {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@GetMapping("/home")
	@PreAuthorize(value = "isAuthenticated()")
	String home(Authentication authentication)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
        = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials());
        return "Hello "+ usernamePasswordAuthenticationToken.getName()+". You are at the home page";
		//return "you are at home page.";
	}
	
	@GetMapping("/guest")
	@PreAuthorize(value = "permitAll()")
	String guest()
	{
		return "you are at guest page.";
	}
	
	@GetMapping("/admin")
	@PreAuthorize(value = "hasAnyAuthority('admin')")
	String amdin()
	{
		return "you are at admin page.";
	}
	
	@PostMapping("/signup")
    @PreAuthorize(value = "permitAll()")
    public void signUp(@RequestBody SignupRequest signupRequest){
        userDetailsService.signUp(signupRequest);
    }
}

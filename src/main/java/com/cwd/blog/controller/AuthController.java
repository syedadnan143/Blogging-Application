package com.cwd.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwd.blog.Security.JwtTokenHelper;
import com.cwd.blog.exceptions.ApiException;
import com.cwd.blog.payloads.JwtAutResponse;
import com.cwd.blog.payloads.JwtAuthRequest;
import com.cwd.blog.payloads.UserDto;
import com.cwd.blog.services.UserSrevice;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/auth/")

public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserSrevice userService;
	
	@PostMapping("/login")
     public ResponseEntity<JwtAutResponse> createToken(
     @RequestBody JwtAuthRequest request ) throws Exception
	{
		System.out.println("\nEntered Login Controller\n");
    	 this.authenticate(request.getUsername(),request.getPassword());
    	UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
    	String token = this.jwtTokenHelper.generateToken(userDetails);
    	System.out.println("token is:" + token);
    	JwtAutResponse response = new JwtAutResponse();
    	response.setToken(token);
    	return new ResponseEntity<JwtAutResponse>(response,HttpStatus.OK);
     }
	
	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}
		catch (BadCredentialsException e) {
			// TODO: handle exception
			System.out.println("invalid details");
		    throw new ApiException("invalid username or password");	
		}
				
	}
	
	// register new user Api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
	UserDto registeredUser=this.userService.registerNewUser(userDto);
	
	return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
} 

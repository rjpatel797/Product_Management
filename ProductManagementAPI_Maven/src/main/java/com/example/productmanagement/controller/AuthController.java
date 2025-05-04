package com.example.productmanagement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productmanagement.DTO.CustomResponse;
import com.example.productmanagement.DTO.UserReqDto;
import com.example.productmanagement.Security.JwtHelper;
import com.example.productmanagement.model.JwtRequest;
import com.example.productmanagement.model.JwtResponse;
import com.example.productmanagement.model.User;
import com.example.productmanagement.repository.UserRepository;
import com.example.productmanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Authenticate to get access JWT token")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
    	doAuthenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    	
        User userDetails = userRepository.findByUsername(authenticationRequest.getUsername());
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username or Password!!");
        }
        
        // Set current datetime to logindate
        userDetails.setLogindate(new Date());
        userRepository.save(userDetails);
        
        if(userDetails.isUserstatus()) {
        	String token = this.helper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .message("Login Successfully!!")
                    .status(true).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
        	JwtResponse response = JwtResponse.builder()
                    .status(false)
                    .message("You Are De-Active Not Login!!")
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    private void doAuthenticate(String username, String password) throws Exception {
    	User userDetails = userRepository.findByUsername(username);
	    if (userDetails == null) {
	        throw new UsernameNotFoundException("Invalid Username!!");
	    }
	    try {
	        manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	    } catch (BadCredentialsException e) {
	        throw new BadCredentialsException("Invalid Password!!");
	    }
	}

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
	public ResponseEntity<CustomResponse> exceptionHandler(Exception e) {
	    String message;
	    if (e instanceof UsernameNotFoundException) {
	        message = "Invalid Username!!";
	    } else if (e instanceof BadCredentialsException) {
	        message = "Invalid Password!!";
	    } else {
	        message = "Invalid Username or Password!!";
	    }
	    
	    CustomResponse response = CustomResponse.builder()
	            .message(message)
	            .status(false)
	            .build();
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
    
    
    //For create new user
    @Operation(summary = "createuser for login")
    @PostMapping("/createuser")
    public String createUser(@RequestBody UserReqDto user) {
        return userService.createUser(user);
    }
    
 
}
    
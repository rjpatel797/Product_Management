package com.example.productmanagement.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.productmanagement.DTO.CustomResponse;
import com.example.productmanagement.DTO.UserReqDto;
import com.example.productmanagement.model.User;
import com.example.productmanagement.repository.UserRepository;
import com.example.productmanagement.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// Method to generate JSON response
    private String generateJsonResponse(boolean status, String message) {
        CustomResponse response = new CustomResponse( status, message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle serialization exception
            return "{\"status\": \"error\", \"message\": \"An error occurred while processing your request\"}";
        }
	}
	
	
	@Override
	public String createUser(@RequestBody UserReqDto user) {
		
		 String message;
	     boolean status;
	     
	     User user2 = new User();
	     user2.setUsername(user.getUsername());
	     user2.setPassword(passwordEncoder.encode(user.getPassword()));
	     
	     if (userRepository.existsByUsername(user.getUsername())) {
	            return generateJsonResponse(false, "Username already exists!!");
	        }
	     
	     try {
	            userRepository.save(user2);
	            message = "User Added Successfully!!";
	            status = true;
	        } catch (Exception e) {
	            message = "Failed to add user!!";
	            status = false;
	        }
	     
	     CustomResponse response = new CustomResponse();
	        response.setStatus(status);
	        response.setMessage(message);
	        
	        // Serialize CustomResponse object to JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        String jsonResponse = null;
	        try {
	            jsonResponse = objectMapper.writeValueAsString(response);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	            // Handle serialization exception
	        }
	        
	        return jsonResponse;
	}



}

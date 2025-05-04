package com.example.productmanagement.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.productmanagement.DTO.UserReqDto;

public interface UserService {

	String createUser(@RequestBody UserReqDto user);
	
}

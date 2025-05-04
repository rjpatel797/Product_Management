package com.example.productmanagement.Security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.productmanagement.DTO.CustomResponse;

public class UserNotFoundException extends UsernameNotFoundException {
    private CustomResponse response;

    public UserNotFoundException(CustomResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public CustomResponse getResponse() {
        return response;
    }
}

package com.example.productmanagement.Security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.productmanagement.DTO.CustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
    	//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		 // PrintWriter writer = response.getWriter(); writer.println("Access Denied !! " + authException.);
		  if(authException.getMessage().contains("User Not Found ")) {
			  String jsonResponse = objectMapper.writeValueAsString(new CustomResponse(false, authException.getMessage()));
	          response.setContentType("application/json");
	          response.getWriter().write(jsonResponse);
		  }
		  else {
			  String jsonResponse = objectMapper.writeValueAsString(new CustomResponse(false, "Authorization failed Please login Again!!!"));
			  response.setContentType("application/json");
			  response.getWriter().write(jsonResponse);
		  }
	}

}

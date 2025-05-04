package com.example.productmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class JsonCustomResponse {

	private boolean status;
	private String message;
	private Object data;
	
}

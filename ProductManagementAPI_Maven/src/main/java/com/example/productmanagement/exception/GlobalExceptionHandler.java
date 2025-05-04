
package com.example.productmanagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductException.class)
    public ResponseEntity<Map<String, Object>> handleStockTransferException(ProductException ex) {
        log.error("Handling CategoryException: " + ex.getMessage()); // Ensure this log prints when the exception is caught
        
        // Prepare response with status and message
        Map<String, Object> response = new HashMap<>();
        response.put("status", false); // Ensure status is always false
        response.put("message", ex.getMessage()); // Use the exception message
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	// Handle generic exceptions to ensure consistent response format
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("Handling unexpected exception: " + ex.getMessage()); // Log the unexpected exception
        
        // Default response for unexpected errors
        Map<String, Object> response = new HashMap<>();
        response.put("status", false); // Ensure status is false
        response.put("message", "An unexpected error occurred. Please try again.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

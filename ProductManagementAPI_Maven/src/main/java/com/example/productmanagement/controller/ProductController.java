
package com.example.productmanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.productmanagement.DTO.JsonCustomResponse;
import com.example.productmanagement.DTO.JsonCustomResponseWebPagination;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    
  	@Operation(summary = "For Create addCategory Details")
  	@PostMapping(value = "/addProduct")
	public ResponseEntity<JsonCustomResponse> addProduct(@RequestBody Product product) {
		JsonCustomResponse response = productService.addProduct(product);
        return ResponseEntity.ok(response);
    }
  	
  	@Operation(summary = "For Get All product Record")
  	@GetMapping(value = "/getAllProduct")
  	public ResponseEntity<JsonCustomResponseWebPagination> getAllClientDetails( 
  			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(required = false) String searchTerm) {

		Page<Map<String, Object>> clientReturn = productService.getAllProduct(page, size, searchTerm);

	    // Create custom response object
	    JsonCustomResponseWebPagination response = new JsonCustomResponseWebPagination();
	    response.setStatus(true); 
	    response.setMessage("Data retrieved successfully!!");
	    response.setData(clientReturn.getContent());
	    response.setCurrentPage(clientReturn.getNumber());
	    response.setPageSize(clientReturn.getSize());
	    response.setTotalPages(clientReturn.getTotalPages());
	    response.setTotalElements(clientReturn.getTotalElements());

	    return ResponseEntity.ok(response);
      }
  	
  	@PutMapping(value = "/updateProduct/{id}")
	public ResponseEntity<JsonCustomResponse> updateProduct(@PathVariable long id, @RequestBody Product product) {
		JsonCustomResponse response = productService.updateProduct(id, product);
        return ResponseEntity.ok(response);
    }
  	
  	@DeleteMapping(value = "/deleteProduct/{id}")
	public ResponseEntity<JsonCustomResponse> deleteManagerDetails(@PathVariable long id) {
		JsonCustomResponse response = productService.deleteManagerDetails(id);
	    return ResponseEntity.ok(response);
    }
}

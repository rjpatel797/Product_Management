package com.example.productmanagement.controller;

import com.example.productmanagement.DTO.JsonCustomResponse;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest1 {

    private static final Logger log = LoggerFactory.getLogger(ProductControllerTest1.class);

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    public ProductControllerTest1() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        log.info("Running testAddProduct");

        Product product = new Product();
        product.setProductname("Laptop");
        product.setDescription("Gaming Laptop");

        JsonCustomResponse mockResponse = new JsonCustomResponse();
        mockResponse.setStatus(true);
        mockResponse.setMessage("Product added successfully");

        when(productService.addProduct(product)).thenReturn(mockResponse);

        ResponseEntity<JsonCustomResponse> response = productController.addProduct(product);

        log.info("testAddProduct - Response: {}", response.getBody().getMessage());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isStatus());
        assertEquals("Product added successfully", response.getBody().getMessage());
    }

    @Test
    void testUpdateProduct() {
        log.info("Running testUpdateProduct");

        long productId = 1L;
        Product product = new Product();
        product.setProductname("Updated Laptop");

        JsonCustomResponse mockResponse = new JsonCustomResponse();
        mockResponse.setStatus(true);
        mockResponse.setMessage("Product updated successfully");

        when(productService.updateProduct(productId, product)).thenReturn(mockResponse);

        ResponseEntity<JsonCustomResponse> response = productController.updateProduct(productId, product);

        log.info("testUpdateProduct - Response: {}", response.getBody().getMessage());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isStatus());
        assertEquals("Product updated successfully", response.getBody().getMessage());
    }
}

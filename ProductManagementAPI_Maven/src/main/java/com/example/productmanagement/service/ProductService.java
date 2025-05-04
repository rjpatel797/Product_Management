package com.example.productmanagement.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.productmanagement.DTO.JsonCustomResponse;
import com.example.productmanagement.model.Product;

public interface ProductService {

	JsonCustomResponse addProduct(Product product);
	Page<Map<String, Object>> getAllProduct(int page, int size, String searchTerm);
	JsonCustomResponse updateProduct(long id, Product product);
	JsonCustomResponse deleteManagerDetails(long id);
}

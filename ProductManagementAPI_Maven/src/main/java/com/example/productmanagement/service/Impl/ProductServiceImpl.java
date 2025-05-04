package com.example.productmanagement.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.productmanagement.DTO.JsonCustomResponse;
import com.example.productmanagement.exception.ProductException;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.ProductRepository;
import com.example.productmanagement.service.ProductService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonCustomResponse addProduct(Product product) {
		 JsonCustomResponse response = new JsonCustomResponse();

		    try {
		        boolean isNameExist = productRepository.existsByProductname(product.getProductname());

		        if (isNameExist) {
		            response.setStatus(false);
		            response.setMessage("product already exists!!");
		            return response;
		        }

		        Date now = new Date();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String formattedDate = dateFormat.format(now);

		        product.setCreatedate(formattedDate);
		        productRepository.save(product);

		        response.setStatus(true);
		        response.setMessage("product saved Successfully!!");
		        log.info("product saved Successfully!!");

		    } catch (Exception e) {
		        log.error("Error while saving product: {}", e.getMessage(), e);
		        response.setStatus(false);
		        throw new ProductException("product failed: " + e.getMessage());
		    }

		    return response;
	}

	@Override
	public Page<Map<String, Object>> getAllProduct(int page, int size, String searchTerm) {
	    Sort sort = Sort.by(Sort.Direction.DESC, "id");
	    PageRequest pageRequest = PageRequest.of(page, size, sort);

	    Page<Product> products;

	    if (searchTerm == null || searchTerm.trim().isEmpty()) {
	        products = productRepository.findAll(pageRequest);
	    } else {
	        products = productRepository.findBySearchTerm(searchTerm, pageRequest);
	    }

	    Page<Map<String, Object>> resultPage = products.map(product -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", product.getId());
	        map.put("productName", product.getProductname());
	        map.put("description", product.getDescription());
	        map.put("price", product.getPrice());
	        map.put("quantity", product.getQuantity());
	        map.put("remark", product.getRemark());
	        map.put("actionDate", product.getActiondate());
	        map.put("createDate", product.getCreatedate());
	        return map;
	    });

	    return resultPage;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonCustomResponse updateProduct(long id, Product product) {
	    JsonCustomResponse response = new JsonCustomResponse();

	    Optional<Product> existingProductOpt = productRepository.findById(id);

	    if (existingProductOpt.isEmpty()) {
	        response.setStatus(false);
	        response.setMessage("Product not found!");
	        return response;
	    }

	    boolean nameExists = productRepository.existsByProductnameExcludingId(product.getProductname(), product.getId());

	    if (nameExists) {
	        response.setStatus(false);
	        response.setMessage("productName already exists!");
	        return response;
	    }

	    try {
	        Product existing = existingProductOpt.get();

	        // Update fields
	        existing.setProductname(product.getProductname());
	        existing.setDescription(product.getDescription());
	        existing.setPrice(product.getPrice());
	        existing.setQuantity(product.getQuantity());
	        existing.setRemark(product.getRemark());
	        existing.setActiondate(product.getActiondate());

	        // Set updated date (if applicable)
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        existing.setCreatedate(sdf.format(new Date()));

	        productRepository.save(existing);

	        response.setStatus(true);
	        response.setMessage("Product updated successfully!!");
	    } catch (Exception e) {
	        log.error("Error updating product: {}", e.getMessage(), e);
	        response.setStatus(false);
	        throw new ProductException("product failed update: " + e.getMessage());
	    }

	    return response;
	}

	@Override
	public JsonCustomResponse deleteManagerDetails(long id) {
	    JsonCustomResponse response = new JsonCustomResponse();

	    try {
	        if (!productRepository.existsById(id)) {
	            response.setStatus(false);
	            response.setMessage("Product not found with ID");
	            return response;
	        }

	        productRepository.deleteById(id);

	        response.setStatus(true);
	        response.setMessage("Product deleted successfully.");
	    } catch (Exception e) {
	        log.error("Error deleting product: {}", e.getMessage(), e);
	        response.setStatus(false);
	        response.setMessage("Failed to delete product.");
	    }

	    return response;
	}

}
 
    
    
	


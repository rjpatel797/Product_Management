package com.example.productmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.productmanagement.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	 boolean existsByProductname(String productname);
	 
	 @Query("SELECT p FROM Product p WHERE " +
	           "LOWER(p.productname) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
	           "LOWER(p.description) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
	           "LOWER(p.price) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
	           "CAST(p.quantity AS string) LIKE %:term% OR " +
	           "LOWER(p.remark) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
	           "CAST(p.actiondate AS string) LIKE %:term% OR " +
	           "CAST(p.createdate AS string) LIKE %:term%")
	    Page<Product> findBySearchTerm(String term, PageRequest pageRequest);
	 
	 @Query("SELECT COUNT(p) > 0 FROM Product p WHERE LOWER(p.productname) = LOWER(:name) AND p.id <> :id")
	 boolean existsByProductnameExcludingId(@Param("name") String name, @Param("id") Long id);


}

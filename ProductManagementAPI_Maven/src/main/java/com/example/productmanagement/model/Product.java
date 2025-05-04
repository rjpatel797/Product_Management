package com.example.productmanagement.model;

import java.io.Serializable;
import java.sql.Date;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "product")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	
	 @Column(name = "productname")
	 private String productname;
	 
	 @Column(name = "description")
	 private String description;
	 
	 @Column(name = "price")
	 private String price;
	 
	 @Column(name = "quantity")
	 private Integer quantity;
	 
	 @Column(name = "remark")
	 private String remark;
	 
	 @Column(name = "actiondate", columnDefinition = "DATE")
	 private Date actiondate;
	 
	 @Column(name = "createdate", columnDefinition="TIMESTAMP")
	 @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	 private String createdate;
	 
	 
}

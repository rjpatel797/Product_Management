package com.example.productmanagement.DTO;

import lombok.Data;

@Data
public class JsonCustomResponseWebPagination {

	private boolean status;
	private String message;
	private Object data;
	private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    
}
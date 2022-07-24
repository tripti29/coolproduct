package com.tpt.saturn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpt.saturn.dto.CreateProductRequest;
import com.tpt.saturn.dto.GetProductResponse;
import com.tpt.saturn.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController {
	@Autowired
	ProductService productService;

	@PostMapping(value="/product", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest createProductRequest) throws Exception{
		log.debug("Create product request for {}", createProductRequest);
		String productName = productService.createProduct(createProductRequest);
		return new ResponseEntity<String>(productName, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/product")
	public ResponseEntity<List<GetProductResponse>> findProduct(@RequestParam(name="id", required = false) Long id, 
											@RequestParam(name="name", required = false) String name,
											@RequestParam(name="category", required = false) String category) throws Exception {
		log.info("Find product request for {}, {}, {}", id, name, category);
		return new ResponseEntity<List<GetProductResponse>>(productService.findProduct(id, name, category), HttpStatus.FOUND);
	}
}

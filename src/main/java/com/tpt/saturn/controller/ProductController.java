package com.tpt.saturn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpt.saturn.dto.CreateProductRequest;
import com.tpt.saturn.dto.GetProductResponse;
import com.tpt.saturn.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService productService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest createProductRequest) throws Exception{
		log.info("Create product request for {}", createProductRequest);
		String productName = productService.createProduct(createProductRequest);
		return new ResponseEntity<String>(productName, HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<GetProductResponse>> findProduct(@RequestParam(name="name", required = false) String name,
											@RequestParam(name="category", required = false) String category) throws Exception {
		log.info("Find product request for {}, {}", name, category);
		return new ResponseEntity<List<GetProductResponse>>(productService.findProducts(name, category), HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/{product-id}")
	public ResponseEntity<GetProductResponse> findProduct(@PathVariable(name="product-id", required = false) Long id)  throws Exception {
		log.info("Find product request for {}, {}, {}", id);
		return new ResponseEntity<GetProductResponse>(productService.findProduct(id), HttpStatus.ACCEPTED);
	}
}

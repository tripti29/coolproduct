package com.tpt.saturn.controller;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpt.saturn.domain.Product;
import com.tpt.saturn.dto.GetProductResponse;


public class AbstractTest {
	
	@Autowired
	ObjectMapper objectMapper;
	
	protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
	      return objectMapper.readValue(json, clazz);
	}
	
	protected GetProductResponse getTestGetProductResponse() {
		GetProductResponse testProduct = new GetProductResponse();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		return testProduct;
	}
	
	protected Product getTestProduct() {
		Product testProduct = new Product();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		return testProduct;
	}
}

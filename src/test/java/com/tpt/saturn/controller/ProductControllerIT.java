package com.tpt.saturn.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.tpt.saturn.controller.ProductControllerAdvice.ErrorResult;
import com.tpt.saturn.dto.CreateProductRequest;
import com.tpt.saturn.dto.GetProductResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/testData.sql")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	//Tests for CreateProduct HTTP.Post
	
	@Test
	public void test_CreateProduct_Success() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		HttpEntity<CreateProductRequest> entity = new HttpEntity<>(testProduct, headers);
		
		ResponseEntity<String> response = this.restTemplate.postForEntity(uri, entity, String.class);
		
		assertEquals(testProduct.getName(), response.getBody());
	}
	
	//Tests for FindProduct HTTP.Get
	
	@Test
	public void test_FindProduct_All_Success() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product");

		ResponseEntity<GetProductResponse[]> responseEntity = restTemplate.getForEntity(uri, GetProductResponse[].class);
		GetProductResponse[] responseList = responseEntity.getBody();
	    
		List<GetProductResponse> productListJsonResponse = Arrays.asList(responseList);
		
		assertFalse(productListJsonResponse.isEmpty());
		
		GetProductResponse productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void test_FindProduct_ById_Success() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product?id=1");

		ResponseEntity<GetProductResponse[]> responseEntity = restTemplate.getForEntity(uri, GetProductResponse[].class);
		GetProductResponse[] responseList = responseEntity.getBody();
	    
		List<GetProductResponse> productListJsonResponse = Arrays.asList(responseList);
		
		assertFalse(productListJsonResponse.isEmpty());
		
		GetProductResponse productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void test_FindProduct_ByName_Success() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product?name=product1");

		ResponseEntity<GetProductResponse[]> responseEntity = restTemplate.getForEntity(uri, GetProductResponse[].class);
		GetProductResponse[] responseList = responseEntity.getBody();
	    
		List<GetProductResponse> productListJsonResponse = Arrays.asList(responseList);
		
		assertFalse(productListJsonResponse.isEmpty());
		
		GetProductResponse productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void test_FindProduct_ByCategory_Success() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product?category=category2");

		ResponseEntity<GetProductResponse[]> responseEntity = restTemplate.getForEntity(uri, GetProductResponse[].class);
		GetProductResponse[] responseList = responseEntity.getBody();
	    
		List<GetProductResponse> productListJsonResponse = Arrays.asList(responseList);
		
		assertFalse(productListJsonResponse.isEmpty());
		
		GetProductResponse productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void test_FindProduct_EmptyParameters_Success() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product?id=&name=&category=");

		ResponseEntity<ErrorResult> responseEntity = restTemplate.getForEntity(uri, ErrorResult.class);
		ErrorResult response = responseEntity.getBody();
	    
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Sorry we cannot find requested data", response.getErrorMessage());
	}
	
	@Test
	public void test_FindProduct_NotFound() throws Exception {
		URI uri = new URI("http://localhost:" + port + "/product?id=5");

		ResponseEntity<ErrorResult> responseEntity = restTemplate.getForEntity(uri, ErrorResult.class);
		ErrorResult response = responseEntity.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Sorry we cannot find requested data", response.getErrorMessage());
	}
}

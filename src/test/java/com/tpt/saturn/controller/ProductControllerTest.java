package com.tpt.saturn.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.tpt.saturn.controller.ProductControllerAdvice.ErrorResult;
import com.tpt.saturn.domain.Product;
import com.tpt.saturn.dto.CreateProductRequest;
import com.tpt.saturn.dto.GetProductResponse;
import com.tpt.saturn.service.ProductService;
import com.tpt.saturn.service.exception.ProductNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest extends AbstractTest{

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProductService productService;
	
	private final Long TEST_ID = 1L;
	private final String TEST_NAME = "testname";
	private final String TEST_CATEGORY = "testcategory";
	
	//Tests for CreateProduct HTTP.Post
	
	@Test
	public void test_CreateProduct_Success() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName(TEST_NAME);
		testProduct.setCategory(TEST_CATEGORY);
		testProduct.setAmount(100);
		
		when(productService.createProduct(testProduct)).thenReturn(TEST_NAME);
		
		String jsonRequestObject = objectMapper.writeValueAsString(testProduct);
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonRequestObject))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		
		assertEquals(response, testProduct.getName());
	}
	
	@Test
	public void test_CreateProduct_ServiceUnavailable() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		when(productService.createProduct(testProduct)).thenAnswer(t -> {throw new Exception();});
		
		String jsonRequestObject = objectMapper.writeValueAsString(testProduct);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products")
		          .contentType(MediaType.APPLICATION_JSON_VALUE)
		          .content(jsonRequestObject))
		          .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
		          .andReturn();
		
		ErrorResult errorResult = new ErrorResult("Sorry we cannot process your request");
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}
	
	//Tests for FindProducts HTTP.Get
	
	@Test
	public void test_FindProduct_ByName_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProducts(TEST_NAME, null)).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products?name=testname")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn();
		  
		String response = mvcResult.getResponse().getContentAsString();
		
		List<Product> productListJsonResponse = Arrays.asList(super.mapFromJson(response, Product[].class));
		
		assertFalse(productListJsonResponse.isEmpty());
		
		Product productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(testProduct.getId(), productJsonResponse.getId());
		assertEquals(testProduct.getName(), productJsonResponse.getName());
		assertEquals(testProduct.getCategory(), productJsonResponse.getCategory());
		assertEquals(testProduct.getAmount(), productJsonResponse.getAmount());		
	}
	
	@Test
	public void test_FindProducts_ByCategory_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProducts(null, TEST_CATEGORY)).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products?category=testcategory")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn();
		  
		String response = mvcResult.getResponse().getContentAsString();
		
		List<Product> productListJsonResponse = Arrays.asList(super.mapFromJson(response, Product[].class));
		
		assertFalse(productListJsonResponse.isEmpty());
		
		Product productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(testProduct.getId(), productJsonResponse.getId());
		assertEquals(testProduct.getName(), productJsonResponse.getName());
		assertEquals(testProduct.getCategory(), productJsonResponse.getCategory());
		assertEquals(testProduct.getAmount(), productJsonResponse.getAmount());		
	}

	@Test
	public void test_FindProducts_EmptyParameters_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProducts("", "")).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products?name=&category=")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn();
		  
		String response = mvcResult.getResponse().getContentAsString();
		
		List<Product> productListJsonResponse = Arrays.asList(super.mapFromJson(response, Product[].class));
		
		assertFalse(productListJsonResponse.isEmpty());
		
		Product productJsonResponse = productListJsonResponse.get(0);
		
		assertEquals(testProduct.getId(), productJsonResponse.getId());
		assertEquals(testProduct.getName(), productJsonResponse.getName());
		assertEquals(testProduct.getCategory(), productJsonResponse.getCategory());
		assertEquals(testProduct.getAmount(), productJsonResponse.getAmount());		
	}
	
	@Test
	public void test_FindProducts_NotFound() throws Exception {
		ErrorResult errorResult = new ErrorResult("Sorry we cannot find requested data");
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
	
		when(productService.findProducts(TEST_NAME, null)).thenAnswer(t -> {throw new ProductNotFoundException();});
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products?name=testname"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andReturn();
		  
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}
	
	@Test
	public void test_FindProducts_ServiceUnavailable() throws Exception {
		ErrorResult errorResult = new ErrorResult("Sorry we cannot process your request");
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
		
		when(productService.findProducts(null, null)).thenAnswer(t -> {throw new Exception();});

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products")
		          .contentType(MediaType.APPLICATION_JSON_VALUE))
		          .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
		          .andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}
	
	//Tests for FindProduct HTTP.Get
	
	@Test
	public void test_FindProduct_ById_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		
		when(productService.findProduct(TEST_ID)).thenReturn(testProduct);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn();
		  
		String response = mvcResult.getResponse().getContentAsString();
		
		Product productJsonResponse = super.mapFromJson(response, Product.class);
		
		assertNotNull(productJsonResponse);
		
		assertEquals(testProduct.getId(), productJsonResponse.getId());
		assertEquals(testProduct.getName(), productJsonResponse.getName());
		assertEquals(testProduct.getCategory(), productJsonResponse.getCategory());
		assertEquals(testProduct.getAmount(), productJsonResponse.getAmount());		
	}
	
	@Test
	public void test_FindProduct_NotFound() throws Exception {
		ErrorResult errorResult = new ErrorResult("Sorry we cannot find requested data");
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
		
		when(productService.findProduct(TEST_ID)).thenAnswer(t -> {throw new ProductNotFoundException();});
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andReturn();
		  
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}
	
	@Test
	public void test_FindProduct_ServiceUnavailable() throws Exception {
		ErrorResult errorResult = new ErrorResult("Sorry we cannot process your request");
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
		
		when(productService.findProduct(TEST_ID)).thenAnswer(t -> {throw new Exception();});

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
		          .contentType(MediaType.APPLICATION_JSON_VALUE))
		          .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
		          .andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}

}

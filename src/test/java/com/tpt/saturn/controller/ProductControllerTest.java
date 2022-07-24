package com.tpt.saturn.controller;

import static org.junit.Assert.assertFalse;
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

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest extends AbstractTest{

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProductService productService;
	
	//Tests for CreateProduct HTTP.Post
	
	@Test
	public void test_CreateProduct_Success() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		when(productService.createProduct(testProduct)).thenReturn("testname");
		
		String jsonRequestObject = objectMapper.writeValueAsString(testProduct);
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/product")
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
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/product")
		          .contentType(MediaType.APPLICATION_JSON_VALUE)
		          .content(jsonRequestObject))
		          .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
		          .andReturn();
		
		ErrorResult errorResult = new ErrorResult("Sorry we cannot process your request");
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}
	
	//Tests for FindProduct HTTP.Get
	
	@Test
	public void test_FindProduct_ById_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProduct(1L, null, null)).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product?id=1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
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
	public void test_FindProduct_ByName_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProduct(null, "testname", null)).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product?name=testname")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
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
	public void test_FindProduct_ByCategory_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProduct(null, null, "testcategory")).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product?category=testcategory")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
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
	public void test_FindProduct_EmptyParameters_Success() throws Exception {
		GetProductResponse testProduct = super.getTestGetProductResponse();
		List<GetProductResponse> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productService.findProduct(null, "", "")).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product?name=&category=")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
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
	public void test_FindProduct_NotFound() throws Exception {
		List<GetProductResponse> productList = new ArrayList<>();
	
		when(productService.findProduct(null, "", "")).thenReturn(productList);
		
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andReturn();
		  
		String response = mvcResult.getResponse().getContentAsString();
		
		List<Product> productListJsonResponse = Arrays.asList(super.mapFromJson(response, Product[].class));
		
		assertTrue(productListJsonResponse.isEmpty());
	}
	
	@Test
	public void test_FindProduct_ServiceUnavailable() throws Exception {

		when(productService.findProduct(null, null, null)).thenAnswer(t -> {throw new Exception();});

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product")
		          .contentType(MediaType.APPLICATION_JSON_VALUE))
		          .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
		          .andReturn();
		
		ErrorResult errorResult = new ErrorResult("Sorry we cannot process your request");
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		String expectedResponseBody = objectMapper.writeValueAsString(errorResult);
		
		assertEquals(actualResponseBody, expectedResponseBody);
	}

}

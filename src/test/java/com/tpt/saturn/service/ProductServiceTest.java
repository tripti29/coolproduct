package com.tpt.saturn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tpt.saturn.controller.AbstractTest;
import com.tpt.saturn.domain.Product;
import com.tpt.saturn.dto.CreateProductRequest;
import com.tpt.saturn.dto.GetProductResponse;
import com.tpt.saturn.repository.ProductRepository;
import com.tpt.saturn.repository.filter.ProductFilter;
import com.tpt.saturn.service.exception.ProductNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest extends AbstractTest{
	@MockBean
	ProductRepository productRepository;
	@Autowired
	ProductService productService;
	
	@Test
	public void test_SaveProduct_Success() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		Product savedTestProduct = new Product();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		when(productRepository.save(any())).thenReturn(savedTestProduct);
		
		String productName = productService.createProduct(testProduct);
		
		assertEquals(savedTestProduct.getName(), productName);
		
	}
	
	@Test
	public void test_SaveProduct_ThrowException() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
		
		when(productRepository.save(any())).thenAnswer(t -> {throw new Exception();});
		
		Assertions.assertThrows(Exception.class, () -> productService.createProduct(testProduct));
		
	}
	
	@Test
	public void test_FindProduct_All_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProduct(null, null, null);
		
		assertEquals(testProduct.getId(), getProductResponses.get(0).getId());
	}
	
	@Test
	public void test_FindProduct_ById_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().id(1L).build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProduct(1L, null, null);
		assertEquals(testProduct.getId(), getProductResponses.get(0).getId());
	}
	
	@Test
	public void test_FindProduct_ByName_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().name("testname").build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProduct(null, "testname", null);
		assertEquals(testProduct.getName(), getProductResponses.get(0).getName());
	}
	
	@Test
	public void test_FindProduct_ByCategory_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().category("testcategory").build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProduct(null, null, "testcategory");
		assertEquals(testProduct.getCategory(), getProductResponses.get(0).getCategory());
	}
	
	@Test
	public void test_FindProduct_ThrowProductNotFoundException() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().id(1L).build();
		List<Product> productList = new ArrayList<>();

		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findProduct(1L, null, null));
	}
	
	@Test
	public void test_FindProduct_ThrowException() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().id(1L).build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenAnswer(t -> {throw new Exception();});
		
		Assertions.assertThrows(Exception.class, () -> productService.findProduct(1L, null, null));
	}
}

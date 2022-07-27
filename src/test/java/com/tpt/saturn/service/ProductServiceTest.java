package com.tpt.saturn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	private final Long TEST_ID = 1L;
	private final String TEST_NAME = "testname";
	private final String TEST_CATEGORY = "testcategory";
	
	@Test
	public void test_SaveProduct_Success() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName(TEST_NAME);
		testProduct.setCategory(TEST_CATEGORY);
		testProduct.setAmount(100);
		
		Product savedTestProduct = new Product();
		testProduct.setName(TEST_NAME);
		testProduct.setCategory(TEST_CATEGORY);
		testProduct.setAmount(100);
		
		when(productRepository.save(any())).thenReturn(savedTestProduct);
		
		String productName = productService.createProduct(testProduct);
		
		assertEquals(savedTestProduct.getName(), productName);
	}
	
	@Test
	public void test_SaveProduct_ThrowException() throws Exception {
		CreateProductRequest testProduct = new CreateProductRequest();
		testProduct.setName(TEST_NAME);
		testProduct.setCategory(TEST_CATEGORY);
		testProduct.setAmount(100);
		
		when(productRepository.save(any())).thenAnswer(t -> {throw new Exception();});
		
		Assertions.assertThrows(Exception.class, () -> productService.createProduct(testProduct));
	}
	
	@Test
	public void test_FindProducts_All_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProducts(null, null);
		
		assertEquals(testProduct.getId(), getProductResponses.get(0).getId());
	}
	
	@Test
	public void test_FindProducts_ByName_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().name(TEST_NAME).build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProducts(TEST_NAME, null);
		assertEquals(testProduct.getName(), getProductResponses.get(0).getName());
	}
	
	@Test
	public void test_FindProducts_ByCategory_Success() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().category(TEST_CATEGORY).build();
		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		List<GetProductResponse> getProductResponses = productService.findProducts(null, TEST_CATEGORY);
		assertEquals(testProduct.getCategory(), getProductResponses.get(0).getCategory());
	}
	
	@Test
	public void test_FindProducts_ThrowProductNotFoundException() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().name(TEST_NAME).build();
		List<Product> productList = new ArrayList<>();

		when(productRepository.findAll(productFilter)).thenReturn(productList);
		
		Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findProducts(TEST_NAME, null));
	}
	
	@Test
	public void test_FindProducts_ThrowException() throws Exception {
		ProductFilter productFilter = ProductFilter.builder().name(TEST_NAME).build();		
		Product testProduct = super.getTestProduct();
		List<Product> productList = new ArrayList<>();
		productList.add(testProduct);
		
		when(productRepository.findAll(productFilter)).thenAnswer(t -> {throw new Exception();});
		
		Assertions.assertThrows(Exception.class, () -> productService.findProducts(TEST_NAME, null));
	}
	
	@Test
	public void test_FindProduct_ById_Success() throws Exception {
		Optional<Product> testProduct = Optional.ofNullable(super.getTestProduct());
		
		when(productRepository.findById(TEST_ID)).thenReturn(testProduct);
		
		GetProductResponse getProductResponse = productService.findProduct(TEST_ID);
		assertEquals(testProduct.get().getId(), getProductResponse.getId());
	}
	
	@Test
	public void test_FindProduct_ThrowProductNotFoundException() throws Exception {
		when(productRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(null));
		
		Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findProduct(1L));
	}
	
	@Test
	public void test_FindProduct_ThrowException() throws Exception {
		when(productRepository.findById(1L)).thenAnswer(t -> {throw new Exception();});
		
		Assertions.assertThrows(Exception.class, () -> productService.findProduct(TEST_ID));
	}
}

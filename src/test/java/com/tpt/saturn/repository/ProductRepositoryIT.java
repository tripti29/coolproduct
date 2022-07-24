package com.tpt.saturn.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.tpt.saturn.controller.AbstractTest;
import com.tpt.saturn.domain.Product;
import com.tpt.saturn.repository.filter.ProductFilter;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = "/testData.sql")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductRepositoryIT {
	
	@Autowired
	ProductRepository productRepository;
	
	@Test
    public void test_create_new_product() {
		Product testProduct = new Product();
		testProduct.setName("testname");
		testProduct.setCategory("testcategory");
		testProduct.setAmount(100);
        
        productRepository.save(testProduct);

        ProductFilter productFilter = ProductFilter.builder().name("testname").build();
        List<Product> savedProductList = productRepository.findAll(productFilter);
        assertThat(savedProductList).isNotNull();

        Product savedProduct = savedProductList.get(0);
        assertEquals(testProduct.getId(), savedProduct.getId());
		assertEquals(testProduct.getName(), savedProduct.getName());
		assertEquals(testProduct.getCategory(), savedProduct.getCategory());
		assertEquals(testProduct.getAmount(), savedProduct.getAmount());	
    }

    @Test
    public void test_find_product_by_filter() {
    	ProductFilter productFilter = ProductFilter.builder().id(1L).build();
    	
    	List<Product> foundProducts = productRepository.findAll(productFilter);
        
    	assertThat(foundProducts).hasSize(1);
    	assertEquals(1L, foundProducts.get(0).getId());
    }


}

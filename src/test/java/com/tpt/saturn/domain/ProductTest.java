package com.tpt.saturn.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class ProductTest {
	
	@Test
	public void test_product_equals_ById() {
		Product product1 = new Product();
		product1.setId(1L);
		Product product2 = new Product();
		product2.setId(1L);
		Product product3 = new Product();
		product3.setId(2L);
		
		assertTrue(product1.equals(product1));
		assertFalse(product1.equals(product3));
	}

}

package com.tpt.saturn.service.exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException() {
        super("Product not found");
    }
}

package com.tpt.saturn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tpt.saturn.service.exception.ProductNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestControllerAdvice
public class ProductControllerAdvice {

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler(Exception.class)
	ErrorResult anyExceptionHandler(Exception ex) {
		return new ErrorResult("Sorry we cannot process your request");
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProductNotFoundException.class)
	ErrorResult notFoundHandler(ProductNotFoundException ex) {
		return new ErrorResult("Sorry we cannot find requested data");
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	static class ErrorResult {
		private String errorMessage;
	}
}

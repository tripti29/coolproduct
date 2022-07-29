package com.tpt.saturn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpt.saturn.domain.Product;
import com.tpt.saturn.dto.CreateProductRequest;
import com.tpt.saturn.dto.GetProductResponse;
import com.tpt.saturn.repository.ProductRepository;
import com.tpt.saturn.repository.filter.ProductFilter;
import com.tpt.saturn.service.exception.ProductNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public String createProduct(CreateProductRequest createProductRequest) {
		try {
			//map createProductRequest to product
			Product product = createProductRequestToProduct(createProductRequest);
			log.info("Creating product {}", product );
			return productRepository.save(product).getName();
		} catch(Exception e) {
			log.error("Failed to save product ", e);
			throw e;
		}
		
	}
	
	public List<GetProductResponse> findProducts(String name, String category) throws Exception {
		List<GetProductResponse> getProductResponses;
		try {
			log.info("Finding products for name {}, and category {}", name, category);
			ProductFilter productFilter = ProductFilter.builder().category(category).name(name).build();
			List<Product> productList = productRepository.findAll(productFilter);
			if(productList == null || productList.isEmpty()) {
				throw new ProductNotFoundException();
			}
			//map product list to createProductRequest list
			getProductResponses = new ArrayList<GetProductResponse>(productList.size());
			productList.forEach(product -> {
				GetProductResponse getProductResponse = productToCreateProductRequest(product);
	        	getProductResponses.add(getProductResponse);
			});
			log.info("Found {} products", productList.size());
		}catch(Exception e) {
			log.error("Failed to find product ", e);
			throw e;
		}
		return getProductResponses;
	}
	
	public GetProductResponse findProduct(Long id) throws Exception {
		GetProductResponse getProductResponse = null;
		try {
			log.info("Finding product for id {}", id);
			Optional<Product> optionalProduct = productRepository.findById(id);
			Product foundProduct = optionalProduct.orElseThrow(() -> new ProductNotFoundException());
			//map product to createProductRequest
			getProductResponse = productToCreateProductRequest(foundProduct);
			log.info("Found {} product", getProductResponse.getId());
		}catch(Exception e) {
			log.error("Failed to find product ", e);
			throw e;
		}
		return getProductResponse;
	}
	
	//Implement Mapstruct
	private Product createProductRequestToProduct(CreateProductRequest createProductRequest) {
		log.info("mapping to Product object");
		Product product = new Product();
		product.setName(createProductRequest.getName());
		product.setCategory(createProductRequest.getCategory());
		product.setAmount(createProductRequest.getAmount());
		return product;
	}
	
	private GetProductResponse productToCreateProductRequest(Product product) {
		GetProductResponse getProductResponse = new GetProductResponse();
		getProductResponse.setId(product.getId());
    	getProductResponse.setName(product.getName());
    	getProductResponse.setCategory(product.getCategory());
    	getProductResponse.setAmount(product.getAmount());
    	return getProductResponse;
	}
}

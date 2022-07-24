package com.tpt.saturn.repository.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tpt.saturn.domain.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductFilter implements Specification<Product>{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String category;
	private String name;

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if(this.getId() != null) {
			return criteriaBuilder.equal(root.get("id"), this.getId());
		}
		if(this.getCategory() != null) {
			return criteriaBuilder.equal(root.get("category"), this.getCategory());
		}
		if(this.getName() != null) {
			return criteriaBuilder.equal(root.get("name"), this.getName());
		}
		return null;
	}

	
}

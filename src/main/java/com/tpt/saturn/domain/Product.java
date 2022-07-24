package com.tpt.saturn.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String category;
	private int amount;
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Date creationdate;
	@UpdateTimestamp
	private Date updatedate;

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Product)){
			return false;
		}
		Product product = (Product) o;
		return Objects.equals(product.getId(), this.getId());
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}

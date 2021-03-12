package com.target.product.entity;

import java.io.Serializable;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("product_data")
public class ProductEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private Integer productId;
	@Column("name")
	private String name;
	@Column("currentprice")
	private CurrentPrice currentprice;

	public ProductEntity() {

	}

	public ProductEntity(Integer productId, String name, CurrentPrice currentprice) {
		super();
		this.productId = productId;
		this.name = name;
		this.currentprice = currentprice;

	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CurrentPrice getCurrentprice() {
		return currentprice;
	}

	public void setCurrentprice(CurrentPrice currentprice) {
		this.currentprice = currentprice;
	}

	
}
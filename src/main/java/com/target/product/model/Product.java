package com.target.product.model;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.target.product.entity.CurrentPrice;

@Table("product_data")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private Integer productId;
	@Column("currentprice")
	private CurrentPrice currentprice;

	public Product() {

	}

	public Product(Integer productId, CurrentPrice currentprice) {
		super();
		this.productId = productId;
		this.currentprice = currentprice;

	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public CurrentPrice getCurrentprice() {
		return currentprice;
	}

	public void setCurrentprice(CurrentPrice currentprice) {
		this.currentprice = currentprice;
	}

}

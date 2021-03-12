package com.target.product.entity;

import java.io.Serializable;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class ProductTablePrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "productid")
	@Column("productid")
	private String productId;

	public ProductTablePrimaryKey() {

	}

	public ProductTablePrimaryKey(String productId) {
		super();
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
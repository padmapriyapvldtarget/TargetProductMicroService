package com.target.product.model;

import java.io.Serializable;

public class RedSky implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer productId;
	private String name;

	public RedSky() {

	}

	public RedSky(Integer productId, String name, String email) {
		super();
		this.productId = productId;
		this.name = name;

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

}

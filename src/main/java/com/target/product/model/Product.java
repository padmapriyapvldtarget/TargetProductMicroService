package com.target.product.model;

import java.io.Serializable;
import com.target.product.entity.CurrentPrice;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer productId;
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

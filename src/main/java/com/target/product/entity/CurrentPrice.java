package com.target.product.entity;

import org.springframework.data.cassandra.core.mapping.Column;

@org.springframework.data.cassandra.core.mapping.UserDefinedType("current_price")
public class CurrentPrice {
	@Column("value")
	private double value;
	@Column("currencycode")
	 private String currencyCode;


	 // Getter Methods 

	 public double getValue() {
	  return value;
	 }

	 public String getCurrencyCode() {
	  return currencyCode;
	 }

	 // Setter Methods 

	 public void setValue(double value) {
	  this.value = value;
	 }

	 public void setCurrencyCode(String currencyCode) {
	  this.currencyCode = currencyCode;
	 }
	}
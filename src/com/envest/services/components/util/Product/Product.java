package com.envest.services.components.util.Product;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Product {
	public ProductType productType;
	public int productId;
	public String bankName;
	public double interestRate;
	public int userProductId;
	
	protected Product(String bankName, double interestRate,int userProductId, int id, ProductType productType)
	{
		this.productType = productType;
		this.productId = id;
		this.bankName = bankName;
		this.interestRate = interestRate;
		this.userProductId = userProductId;
	}
}

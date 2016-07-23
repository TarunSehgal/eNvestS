package com.eNvestDetails.Products;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Product {
	public ProductType productType;
	public int productId;
	public String bankName;
}

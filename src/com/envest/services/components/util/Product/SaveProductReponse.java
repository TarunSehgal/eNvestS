package com.envest.services.components.util.Product;

public class SaveProductReponse {

	public String ErrorMsg = null;
	public StatusCode statusCode;
	public ErrorCodes errorCodes;
	public int userProductId;
	public Long userKey;
	
	public SaveProductReponse(Long userKey, int productId, StatusCode status)
	{
		this.userKey = userKey;
		this.userProductId = productId;
		this.statusCode = status;		
	}
}

package com.eNvestDetails.util.Product;

public class SaveProductReponse {

	public String ErrorMsg = null;
	public StatusCode statusCode;
	public ErrorCodes errorCodes;
	public int productId;
	public String userId;
	
	public SaveProductReponse(String userId, int productId, StatusCode status)
	{
		this.userId = userId;
		this.productId = productId;
		this.statusCode = status;		
	}
}

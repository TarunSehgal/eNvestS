package com.eNvestDetails.Exception;

import com.eNvestDetails.Response.EnvestResponse;


public class SuccessMessage extends EnvestResponse  {
	
	private int code;
	
	private String message;
		
	public SuccessMessage(int code,String msg,String status){
		this.code = code;
		this.message=msg;
		setStatus(status);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}	
}

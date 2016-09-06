package com.eNvestDetails.Exception;

import com.eNvestDetails.Response.EnvestResponse;


public class ErrorMessage extends EnvestResponse  {
	
	private int code;
	
	private String message;
		
	public ErrorMessage(int code,String msg,String status){
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

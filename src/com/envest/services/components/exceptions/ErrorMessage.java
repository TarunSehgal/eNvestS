package com.envest.services.components.exceptions;

import com.envest.services.response.EnvestResponse;


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

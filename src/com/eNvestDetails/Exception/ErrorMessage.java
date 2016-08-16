package com.eNvestDetails.Exception;

import com.eNvestDetails.Response.EnvestResponse;


public class ErrorMessage extends EnvestResponse  {
	
	private int code;
	
	private String message;
	
	private String type;
	
	public ErrorMessage(int code,String msg,String type,String status){
		this.code = code;
		this.message=msg;
		this.type=type;
		setStatus(status);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getType() {
		return type;
	}
	
}

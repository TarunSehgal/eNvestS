package com.eNvestDetails.Exception;

import com.eNvestDetails.Response.EnvestResponse;


public class ErrorMessage extends EnvestResponse  {
	
	private int code;
	
	private String message;
	
	private String type;

	
	public ErrorMessage(){
		
	}
	
	
	public ErrorMessage(int code,String msg,String type,String status){
		this.code = code;
		this.message=msg;
		this.type=type;
		setStatus(status);
	}



	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}

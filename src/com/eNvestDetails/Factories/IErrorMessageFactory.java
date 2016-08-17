package com.eNvestDetails.Factories;

import com.eNvestDetails.Exception.ErrorMessage;

public interface IErrorMessageFactory {
	public ErrorMessage getServerErrorMessage(String msg);
	
	public ErrorMessage getFailureMessage(int returnCode,String msg);
	
	public ErrorMessage getSuccessMessage(int returnCode,String msg);
	
	public ErrorMessage getMessage(int returnCode,String msg,String status);
	
	public ErrorMessage getMessage(int returnCode,String msg,String type, String status);
}

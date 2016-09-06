package com.eNvestDetails.Factories;

import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Exception.SuccessMessage;

public interface IEnvestMessageFactory {
	public ErrorMessage getServerErrorMessage(String msg);
	
	public ErrorMessage getFailureMessage(int returnCode,String msg);
	
	public SuccessMessage getSuccessMessage(String msg);
	
	public ErrorMessage getMessage(int returnCode,String msg,String status);
}

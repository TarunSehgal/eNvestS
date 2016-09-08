package com.envest.services.components;

import com.envest.services.components.exceptions.ErrorMessage;
import com.envest.services.components.exceptions.SuccessMessage;

public interface IEnvestMessageFactory {
	public ErrorMessage getServerErrorMessage(String msg);
	
	public ErrorMessage getFailureMessage(int returnCode,String msg);
	
	public SuccessMessage getSuccessMessage(String msg);
	
	public ErrorMessage getMessage(int returnCode,String msg,String status);
}

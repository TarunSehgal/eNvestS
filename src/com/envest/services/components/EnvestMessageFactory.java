package com.envest.services.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.ErrorMessage;
import com.envest.services.components.exceptions.SuccessMessage;

@Component
public class EnvestMessageFactory implements IEnvestMessageFactory {

	@Autowired
	private MessageFactory messageFactory = null;
	
	public EnvestMessageFactory(){}
	public ErrorMessage getServerErrorMessage(String msg){
		return new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
				,msg
				,messageFactory.getMessage("message.failure"));	
	}
	
	public ErrorMessage getFailureMessage(int returnCode,String msg){
		return new ErrorMessage(returnCode
				,msg
				,messageFactory.getMessage("message.failure"));	
	}	
	
	public SuccessMessage getSuccessMessage(String msg){
		return new SuccessMessage(EnvestConstants.RETURN_CODE_SUCCESS
				,msg
				,messageFactory.getMessage("message.success"));	
	}
	
	public ErrorMessage getMessage(int returnCode,String msg,String status){
		return new ErrorMessage(returnCode
				,msg
				,status);	
	}
}

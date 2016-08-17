package com.eNvestDetails.Factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.constant.EnvestConstants;

@Component
public class ErrorMessageFactory implements IErrorMessageFactory {

	@Autowired
	private MessageFactory messageFactory = null;
	
	public ErrorMessageFactory(){}
	public ErrorMessage getServerErrorMessage(String msg){
		return new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
				,msg
				,null
				,messageFactory.getMessage("message.failure"));	
	}
	
	public ErrorMessage getFailureMessage(int returnCode,String msg){
		return new ErrorMessage(returnCode
				,msg
				,null
				,messageFactory.getMessage("message.failure"));	
	}	
	
	public ErrorMessage getSuccessMessage(int returnCode,String msg){
		return new ErrorMessage(returnCode
				,msg
				,null
				,messageFactory.getMessage("message.success"));	
	}
	
	public ErrorMessage getMessage(int returnCode,String msg,String status){
		return new ErrorMessage(returnCode
				,msg
				,null
				,status);	
	}
	
	public ErrorMessage getMessage(int returnCode,String msg,String type, String status){
		return new ErrorMessage(returnCode
				,msg
				,type
				,status);	
	}	
}

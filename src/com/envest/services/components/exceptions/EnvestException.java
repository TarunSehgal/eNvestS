package com.envest.services.components.exceptions;

public class EnvestException extends Exception {

	private ErrorMessage errorMessage;
	
	public EnvestException(ErrorMessage message) {
		errorMessage = message;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}

package com.envest.services.components.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {
	
	@Autowired
	private ResourceBundleMessageSource messageBundle = null;
	Locale locale = new Locale("en_US");
	
	public String getMessage(String message){
		return messageBundle.getMessage(message, null, locale);
	}

}

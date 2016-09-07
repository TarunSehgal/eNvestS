package com.eNvestDetails.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.ConfigFactory;

@Component
public class CommonUtil {
	
	@Autowired
	private ConfigFactory config;
	
	public String getGte(String range){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, Integer.valueOf(range) * -1);
		return dateFormat.format(c.getTime());
	}
	
	public boolean isTestUser(String accessToken){
		if(null != accessToken){
			return accessToken.startsWith("test");
		}
		return false;		
	}

}

package com.envest.services.components.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.envest.dal.IUserInfoDAOService;
import com.envest.dal.UserInfoDAOService;

public class EnvestInterceptor extends HandlerInterceptorAdapter {
	
	public static final Logger log = Logger.getLogger(EnvestInterceptor.class.getName());
	
	private IUserInfoDAOService daoAdapter = new UserInfoDAOService();
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
		log.info("testing db connection");
		boolean value = daoAdapter.testConnection();
		int i = 0;
		while(!value && i< 5){
			value = daoAdapter.testConnection();
			i++;
		}
		
		log.info("testing connection status: "+ value);
        return true;
    }

}

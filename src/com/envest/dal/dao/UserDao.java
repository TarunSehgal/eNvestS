package com.envest.dal.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.envest.dal.dto.UserInfoDTO;
import com.envest.security.User;
import com.envest.services.components.exceptions.EnvestException;

class UserDao implements UserDetailsService  {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfoDTO dto;
		try {
			dto = UserInfoDao.authenticateUser(username, null);
		} catch (EnvestException e) {			
			throw  new UsernameNotFoundException("User not found");
		}
		
		if(null == dto){
			throw  new UsernameNotFoundException("User not found");
		}
		User details = new User(dto.getEnvestUserID(), dto.getPassword()); 
		details.setId(dto.getUserkey());
	
		return details;
	}

}

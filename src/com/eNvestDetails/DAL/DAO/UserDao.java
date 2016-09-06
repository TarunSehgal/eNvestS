package com.eNvestDetails.DAL.DAO;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eNvestDetails.DAL.DTO.UserInfoDTO;
import com.eNvestDetails.security.User;

class UserDao implements UserDetailsService  {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfoDTO dto = UserInfoDao.authenticateUser(username, null);
		
		if(null == dto){
			throw  new UsernameNotFoundException("User not found");
		}
		User details = new User(dto.getEnvestUserID(), dto.getPassword()); 
		details.setId(dto.getUserkey());
	
		return details;
	}

}

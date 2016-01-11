package com.hao123.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hao123.security.entity.UserDetailImpl;

public class UserDetailsServiceImpl implements UserDetailsService{
	

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailImpl userDetail = new UserDetailImpl();
		userDetail.setId(1L);
		userDetail.setUsername("dobby");
		userDetail.setPassword("dobby");
		return userDetail;
	}

}

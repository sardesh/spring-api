package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * Needed by spring security to load user while authenticating
 * @author Sardesh Sharma
 *
 */

@Service
public class CustomUserService implements UserDetailsService {
	
	@Autowired
	UserService userDetailsService;
	
	private Logger logger = Logger.getLogger(CustomUserService.class);
	
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		if(logger.isInfoEnabled()){
			logger.info("+loadUserByUsername()");
		}
		User user= userDetailsService.findUserByUsername(userName);
		if(user == null){
			if(logger.isErrorEnabled()){
				logger.error("-loadUserByUsername()");
			}
			throw new UsernameNotFoundException("User not found");
		}
		
		if(logger.isInfoEnabled()){
			logger.info("-loadUserByUsername()");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),true, true, true, true,getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		 List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		 
			 grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
			
		
		return grantedAuthorities;
	}

}

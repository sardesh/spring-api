package com.example.demo.service;


import java.util.List;

import com.example.demo.Exception.GenericException;
import com.example.demo.model.User;

/**
 * interface for user service
 * @author Sardesh Sharma
 *
 */
public interface UserService {
		
		User findUserByUsername(String username);
		User findUserByUsernameAndPassword(String username,String password);
		User createUser(User user) throws GenericException;
		boolean isUserExists(User user); 
}

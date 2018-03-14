package com.example.demo.service.impl;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.GenericException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.ErrorConstants;
import com.example.demo.utils.ErrorMessage;

/**
 * User Service layer
 * @author Sardesh Sharma
 *
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	
/**
 * fetched user by username
 */
	@Override
	public User findUserByUsername(String username) {
		if(logger.isInfoEnabled()){
			logger.info("findUserByUsername()");
		}
		return userRepository.findOne(username);
	}

	/**
	 * creates a user
	 */

	@Override
	public User createUser(User user) throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("+createUser()");
		}
		if(!isUserExists(user))
		return userRepository.save(user);
		
		if(logger.isInfoEnabled()){
			logger.info("-createUser()");
		}
		throw new GenericException(ErrorConstants.USER_EXIST,ErrorMessage.USER_EXISTS);
	}

	/**
	 * checks for existing user
	 */
	@Override
	public boolean isUserExists(User user) {
		if(logger.isInfoEnabled()){
			logger.info("+isUserExists()");
		}
		User dbuser = userRepository.findOne(user.getUsername());
		if(logger.isInfoEnabled()){
			logger.info("-isUserExists()");
		}
		if(dbuser == null )
			return false;
		else
			return true;
	
	}

	/**
	 * fetches user by username and password
	 */
	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		if(logger.isInfoEnabled()){
			logger.info("findUserByUsernameAndPassword()");
		}
		return userRepository.findUserByUsernameAndPassword(username, password);
	}

}

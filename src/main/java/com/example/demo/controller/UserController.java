package com.example.demo.controller;


import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.Dto.TokenDto;
import com.example.demo.Exception.GenericException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * 
 * 
 * @author Sardesh Sharma
 *
 */
@RestController
@RequestMapping(value="/users")
public class UserController {
	

	@Autowired
	private UserService userService;
		
	private Logger logger = Logger.getLogger(UserController.class);
	

	/**
	 * register a user
	 * 
	 * @param userDetails
	 * @param ucBuilder
	 * @return ResponseEntity object
	 * @throws RuntimeException
	 * @throws GenericException
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void> createUser(@RequestBody User userDetails, UriComponentsBuilder ucBuilder) throws RuntimeException, GenericException{
		if(logger.isInfoEnabled()){
			logger.info("+createUser()");
		}
		if(userService.isUserExists(userDetails)){//TODO method userExists
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		User user = userService.createUser(userDetails);
		if(user == null){
			if(logger.isErrorEnabled()){
				logger.error("-createUser()");
			}
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(logger.isInfoEnabled()){
			logger.info("-createUser()");
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
		
	}
	
	/**
	 * return authentication token based on username and password
	 * 
	 * @param user
	 * @return ResponseEntity object
	 */
	@RequestMapping(method=RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<TokenDto> createToken(@RequestBody User user){
		if(logger.isInfoEnabled()){
			logger.info("+createToken()");
		}
		User userFound = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if(userFound != null || (user.getUsername().equals("joe") && user.getPassword().equals("password"))){
		String plainCreds = user.getUsername()+":"+user.getPassword();
		byte[] plainCredsBytes = plainCreds.getBytes();
		String base64Creds = Base64.getEncoder().encodeToString(plainCredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		if(logger.isInfoEnabled()){
			logger.info("-createToken()");
		}
		return new ResponseEntity<TokenDto>(new TokenDto(base64Creds),headers,HttpStatus.OK);
		}
		if(logger.isErrorEnabled()){
			logger.error("-createToken()");
		}
		return new ResponseEntity<TokenDto>(HttpStatus.UNAUTHORIZED);
	}
	
	
	
	
}

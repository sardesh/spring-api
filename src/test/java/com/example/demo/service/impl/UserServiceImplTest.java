package com.example.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.Exception.GenericException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.ErrorConstants;
import com.example.demo.utils.ErrorMessage;

/**
 * Unit test for user service layer
 * @author Sardesh Sharma
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	
	
	
	@Test
	public void test_findUserByUsername(){
		when(userRepository.findOne(getPredefinedDummyUser().getUsername())).thenReturn(getPredefinedDummyUser());
		 User user = userServiceImpl.findUserByUsername(getPredefinedDummyUser().getUsername());		 
		 assertEquals(getPredefinedDummyUser(), user);
	}
	
	@Test
	public void test_createUser_success() throws GenericException{
		when(userRepository.save(getPredefinedDummyUser())).thenReturn(getPredefinedDummyUser());
		when(userRepository.findOne(getPredefinedDummyUser().getUsername())).thenReturn(null);
		 User user = userServiceImpl.createUser(getPredefinedDummyUser());		 
		 assertEquals(getPredefinedDummyUser(), user);
	}
	
	@Test
	public void test_createUser_failed_as_user_already_exists() {
		when(userRepository.save(getPredefinedDummyUser())).thenReturn(getPredefinedDummyUser());
		when(userRepository.findOne(getPredefinedDummyUser().getUsername())).thenReturn(getPredefinedDummyUser());

		 User user;
		try {
			user = userServiceImpl.createUser(getPredefinedDummyUser());
		} catch (GenericException e) {
			assertEquals(ErrorConstants.USER_EXIST, e.getCode());
			assertEquals(ErrorMessage.USER_EXISTS, e.getMessage());
		}		 
		
	}
	
	@Test
	public void test_isUserExists_true(){
		when(userRepository.findOne(getPredefinedDummyUser().getUsername())).thenReturn(getPredefinedDummyUser());

		boolean userExist = userServiceImpl.isUserExists(getPredefinedDummyUser());
		
		assertTrue(userExist);
	}
	@Test
	public void test_isUserExists_false(){
		when(userRepository.findOne(getPredefinedDummyUser().getUsername())).thenReturn(null);

		boolean userExist = userServiceImpl.isUserExists(getPredefinedDummyUser());
		
		assertFalse(userExist);
	}
	
	
	
	private User getPredefinedDummyUser(){
		User user = new User();
		user.setUsername("test username");
		user.setPassword("test password");
		return user;
	}
	
	private List<User> getPredefinedListOfDummyUser(){
		ArrayList<User> usersList = new ArrayList<User>();
		
		User user = new User();
		user.setUsername("test username1");
		user.setPassword("test password1");
		usersList.add(user);
		user = new User();
		user.setUsername("test username2");
		user.setPassword("test password2");
		usersList.add(user);
		user = new User();
		user.setUsername("test username3");
		user.setPassword("test password3");
		usersList.add(user);
		
		return usersList;
	}
	
	
}

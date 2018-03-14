package com.example.demo.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@TestPropertySource(
  locations = "classpath:application-test.properties")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	
	@Test
	public void test_create_and_find_user(){
		User user = userRepository.save(getPredefinedUserObject());
		assertNotNull(user);
		 User userFound = userRepository.findUserByUsernameAndPassword(getPredefinedUserObject().getUsername(), getPredefinedUserObject().getPassword());
		 assertEquals(user, userFound);
		
	}
	
	
	private User getPredefinedUserObject(){
		User user = new User();
		user.setUsername("test");
		user.setPassword("testpwd");
		return user;
	}
}

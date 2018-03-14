package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.User;

/**
 * Repository/Dao layer to perform CRUD operations on User model
 * 
 * @author Sardesh Sharma
 *
 */
public interface UserRepository extends CrudRepository<User, String> {

	User findUserByUsernameAndPassword(String username,String password);
}

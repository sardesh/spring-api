package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Contact;

/**
 * Repository/Dao layer for contacts to perfom CRUD operations
 * @author Sardesh Sharma
 *
 */
public interface ContactsRepository extends JpaRepository<Contact, Long> {

	/**
	 * select contact based on username
	 * 
	 * @param username
	 * @return List<Contact>
	 */
	List<Contact> findContactByUsername(String username);
	
	/**
	 * select contact based on its id and username
	 * 
	 * @param id
	 * @param username
	 * @return Contact
	 */
	Contact findByIdAndUsername(long id,String username);
}

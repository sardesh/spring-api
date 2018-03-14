package com.example.demo.service;

import java.util.List;

import com.example.demo.Exception.GenericException;
import com.example.demo.model.Contact;
import com.example.demo.model.Phone;

/**
 * Interface for contact service
 * @author Sardesh Sharma
 *
 */
public interface ContactsService {

	List<Contact> fetchAll() throws GenericException;
	
	Long createContact(Contact contact) throws GenericException;
	
	void deleteContact(Long id,String username) throws GenericException;
	
	Contact updateContact(Contact contact,Long id,String username) throws GenericException;

	Long updatePhone(Phone phone, Long contactId,String username) throws GenericException;

	List<Contact> fetchContactsByUser(String username) throws GenericException;;
	
}

package com.example.demo.service.impl;


import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.Exception.GenericException;
import com.example.demo.model.Contact;
import com.example.demo.model.Phone;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.service.ContactsService;
import com.example.demo.utils.ErrorConstants;
import com.example.demo.utils.ErrorMessage;

/**
 * 
 * @author Sardesh Sharma
 *
 */
@Service
public class ContactsServiceImpl implements ContactsService {

	@Autowired
	ContactsRepository contactsRepository;
	
	@Autowired
	PhoneRepository phoneRepository;
	
	private Logger logger = Logger.getLogger(ContactsServiceImpl.class);
	
	/**
	 * Service layer to fecthall contacts 
	 */
	@Override
	public List<Contact> fetchAll() throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("+fetchAll()");
		}
		List<Contact> contacts = contactsRepository.findAll();
		
		if(CollectionUtils.isEmpty(contacts)){
			if(logger.isErrorEnabled()){
				logger.error("-fetchAll()");
			}
			throw new GenericException(ErrorConstants.EMPTY,ErrorMessage.EMPTY);
		}
		if(logger.isInfoEnabled()){
			logger.info("-fetchAll()");
		}
		
		return contacts;
	}
	/**
	 * Service layer to fecthall contacts based on user
	 * 
	 */
	@Override
	public List<Contact> fetchContactsByUser(String username) throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("-fetchContactsByUser()");
		}
		List<Contact> contacts = contactsRepository.findContactByUsername(username);
		
		if(CollectionUtils.isEmpty(contacts)){
			if(logger.isErrorEnabled()){
				logger.error("-fetchContactsByUser()");
			}
			throw new GenericException(ErrorConstants.EMPTY,ErrorMessage.EMPTY);
		}
		
		if(logger.isInfoEnabled()){
			logger.info("-fetchContactsByUser()");
		}
		return contacts;
	}
	
	/**
	 * For creating contact
	 */

	@Override
	public Long createContact(Contact contact) throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("+createContact()");
		}
		contact = contactsRepository.save(contact);
		if(contact == null || contact.getId() == 0){
			if(logger.isErrorEnabled()){
				logger.error("-createContact()");
			}
			throw new GenericException(ErrorConstants.NOT_CREATED, ErrorMessage.NOT_CREATED);
		}
		if(logger.isInfoEnabled()){
			logger.info("-createContact()");
		}
		return contact.getId();
	}
	/**
	 * for deleting contact
	 */

	@Override
	public void deleteContact(Long id,String username) throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("+deleteContact()");
		}
		Contact contact = contactsRepository.findByIdAndUsername(id,username);
		if(contact == null){
			if(logger.isErrorEnabled()){
				logger.error("-deleteContact()");
			}
			throw new GenericException(ErrorConstants.EMPTY,ErrorMessage.EMPTY);
		}
		
		contactsRepository.delete(contact);
		
		if(logger.isInfoEnabled()){
			logger.info("-deleteContact()");
		}
	}

	/**
	 * for updating contact info
	 */
	@Override
	public Contact updateContact(Contact contact, Long id,String username) throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("+updateContact()");
		}
		Contact contactToBeUpdated = contactsRepository.findByIdAndUsername(id,username);
		
		
		if(contactToBeUpdated == null){
			if(logger.isErrorEnabled()){
				logger.error("-updateContact()");
			}
			throw new GenericException(ErrorConstants.EMPTY,ErrorMessage.EMPTY);
		}
		if(contact.getFirstName() != null)
			contactToBeUpdated.setFirstName(contact.getFirstName());
		
		if(contact.getLastName() != null)
			contactToBeUpdated.setLastName(contact.getLastName());
		
			contactsRepository.save(contactToBeUpdated);
			if(logger.isInfoEnabled()){
				logger.info("-updateContact()");
			}
		return contactToBeUpdated;
	}

	/**
	 * for updating phone number
	 */
	@Override
	public Long updatePhone(Phone phone, Long contactId,String username) throws GenericException {
		if(logger.isInfoEnabled()){
			logger.info("+updateContact()");
		}
		Contact contactToBeUpdated = contactsRepository.findByIdAndUsername(contactId,username);
		if(contactToBeUpdated == null){
			if(logger.isErrorEnabled()){
				logger.error("-updateContact()");
			}
			throw new GenericException(ErrorConstants.EMPTY,ErrorMessage.EMPTY);
		}
		if(phone.getPhoneNumber() == null || phone.getPhoneNumber().length() == 0){
			throw new GenericException(ErrorConstants.PHONE_EMPTY,ErrorMessage.PHONE_EMPTY);
		}
		Set<Phone> phones = contactToBeUpdated.getPhones();
		for(Phone p: phones){
			if(p.equals(phone)) throw new GenericException(ErrorConstants.PHONE_EXIST,ErrorMessage.PHONE_EXIST);
		}
		phone.setContact(contactToBeUpdated);
		Phone updatedPhone=	phoneRepository.save(phone);
		if(logger.isInfoEnabled()){
			logger.info("-updateContact()");
		}
		return updatedPhone.getContact().getId();
	}

}

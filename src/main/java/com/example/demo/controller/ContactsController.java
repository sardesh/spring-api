package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.ContactDto;
import com.example.demo.Exception.GenericException;
import com.example.demo.model.Contact;
import com.example.demo.model.Phone;
import com.example.demo.service.ContactsService;

/**
 * 
 * @author Sardesh Sharma
 *
 */
@RestController
@RequestMapping(value="/contacts")
public class ContactsController {

	@Autowired
	ContactsService contactsService;
	
	private Logger logger = Logger.getLogger(ContactsController.class);
	
	/**
	 * fetches all contacts based on user
	 * 
	 * @return ResponseEntity object
	 * @throws GenericException
	 */
	@RequestMapping(method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<ContactDto>> fetchAllContact() throws GenericException{
		
		if(logger.isInfoEnabled()){
			logger.info("+fetchAllContact()");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Contact> contacts = contactsService.fetchContactsByUser(username);
		List<ContactDto> contactDtos = new ArrayList<ContactDto>();
		contacts.stream()
				.forEach( contact -> {
					ContactDto contactDto = null;
					if(!CollectionUtils.isEmpty(contact.getPhones())){
							
						 contactDto = new ContactDto(contact.getId(),contact.getFirstName(),contact.getLastName(),contact.getPhones().stream()
											.map(p -> p.getPhoneNumber())
											.toArray(String[]::new)); 
					}else{
						 contactDto = new ContactDto(contact.getId(),contact.getFirstName(),contact.getLastName(),null); 
					}
					contactDtos.add(contactDto);
				});
		if(logger.isInfoEnabled()){
			logger.info("-fetchAllContact() :"+contactDtos);
		}		
		return new ResponseEntity<List<ContactDto>>(contactDtos,HttpStatus.OK);
	}
	
	/**
	 * create contacts for authenticated user
	 * @param contact
	 * @return ResponseEntity object
	 * @throws GenericException
	 */
	@RequestMapping(method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Contact> createContact(@RequestBody @Valid Contact contact) throws GenericException{
		if(logger.isInfoEnabled()){
			logger.info("+createContact()");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		contact.setUsername(username);
		Long id = contactsService.createContact(contact);
		Contact newContact = new Contact();
		newContact.setId(id);
		if(logger.isInfoEnabled()){
			logger.info("-createContact()");
		}
		return new ResponseEntity<Contact>(newContact,HttpStatus.CREATED);
	}
	
	/**
	 * updates contact for authenticated user
	 * 
	 * @param contact
	 * @param contactId
	 * @return ResponseEntity object
	 * @throws GenericException
	 */
	@RequestMapping(value="/{contactId}",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Contact> updateContactName(@RequestBody Contact contact,@PathVariable("contactId") Long contactId) throws GenericException{
		if(logger.isInfoEnabled()){
			logger.info("+updateContactName()");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Contact updatedContact = contactsService.updateContact(contact, contactId,username);
		updatedContact.setFirstName(null);
		updatedContact.setLastName(null);
		updatedContact.setPhones(null);
		if(logger.isInfoEnabled()){
			logger.info("-updateContactName()");
		}
		return new ResponseEntity<Contact>(updatedContact,HttpStatus.OK);
	}
	
	/**
	 * 
	 * deletes contact for authenticates user
	 * 
	 * @param contactId
	 * @return ResponseEntity object
	 * @throws GenericException
	 */
	@RequestMapping(value="/{contactId}",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> deleteContact(@PathVariable("contactId") Long contactId) throws GenericException{
		if(logger.isInfoEnabled()){
			logger.info("+deleteContact()");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		contactsService.deleteContact(contactId,username);
		if(logger.isInfoEnabled()){
			logger.info("-deleteContact()");
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * 
	 * update phone number for authenticated user
	 * 
	 * @param phone
	 * @param contactId
	 * @return ResponseEntity object
	 * @throws GenericException
	 */
	@RequestMapping(value="/{contactId}/entries",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Contact> updateContactPhone(@RequestBody @Valid Phone phone, @PathVariable("contactId") long contactId) throws GenericException{
		if(logger.isInfoEnabled()){
			logger.info("+pdateContactPhone()");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Long id = contactsService.updatePhone(phone, contactId,username);
		
		Contact newContact = new Contact();
		newContact.setId(id);
		if(logger.isInfoEnabled()){
			logger.info("-updateContactPhone()");
		}
		return new ResponseEntity<Contact>(newContact,HttpStatus.OK);
	}
	
}

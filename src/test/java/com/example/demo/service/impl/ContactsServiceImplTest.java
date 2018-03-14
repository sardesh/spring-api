package com.example.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.Exception.GenericException;
import com.example.demo.model.Contact;
import com.example.demo.model.Phone;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.utils.ErrorConstants;
import com.example.demo.utils.ErrorMessage;

/**
 * Unit test for service layer
 * @author Sardesh Sharma
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ContactsServiceImplTest {

	@InjectMocks
	private ContactsServiceImpl contactsService;
	
	@Mock
	private ContactsRepository contactsRepository;
	
	@Mock
	private PhoneRepository phoneRepository;
	
	@Test
	public void test_fetchAll_if_contacts_are_there() throws GenericException{
		when(contactsRepository.findAll()).thenReturn(getPreDefinedListOfContactObject());
		List<Contact> contacts = contactsService.fetchAll();
		assertNotNull(contacts);
		assertEquals(3, contacts.size());
		assertEquals("test first name1", contacts.get(0).getFirstName());
		assertEquals("test last name1", contacts.get(0).getLastName());
		
		
	}
	
	@Test
	public void test_fetchAll_if_no_contacts_are_there() throws GenericException{
		when(contactsRepository.findAll()).thenReturn(new ArrayList<>());
		try{
		List<Contact> contacts = contactsService.fetchAll();
		}catch (GenericException e) {
			assertEquals(ErrorConstants.EMPTY,e.getCode());
			assertEquals(ErrorMessage.EMPTY,e.getMessage());
		}
		
	}
	
	@Test
	public void test_fetchContactsByUser_if_contacts_are_there() throws GenericException{
		when(contactsRepository.findContactByUsername("joe")).thenReturn(getPreDefinedListOfContactObject());
		List<Contact> contacts = contactsService.fetchContactsByUser("joe");
		assertNotNull(contacts);
		assertEquals(3, contacts.size());
		assertEquals("test first name1", contacts.get(0).getFirstName());
		assertEquals("test last name1", contacts.get(0).getLastName());
		
		
	}
	
	@Test
	public void test_fetchContactsByUser_if_no_contacts_are_there() throws GenericException{
		when(contactsRepository.findContactByUsername("joe")).thenReturn(new ArrayList<>());
		
		try{
			List<Contact> contacts = contactsService.fetchContactsByUser("joe");
		}catch (GenericException e) {
			assertEquals(ErrorConstants.EMPTY,e.getCode());
			assertEquals(ErrorMessage.EMPTY,e.getMessage());
		}
		
	}
	@Test
	public void test_createContact_when_successful() throws GenericException{
		when(contactsRepository.save(getPreDefinedTestContactObjectBeforeCreation())).thenReturn(getPreDefinedTestContactObject());
		
		Long id = contactsService.createContact(getPreDefinedTestContactObjectBeforeCreation());
		
		assertEquals(4, id.longValue());
	
	}
	@Test
	public void test_createContact_when_unsuccessful(){
		when(contactsRepository.save(getPreDefinedTestContactObjectBeforeCreation())).thenReturn(null);
		try{
		Long id = contactsService.createContact(getPreDefinedTestContactObjectBeforeCreation());
		}catch (GenericException e) {
			assertEquals(ErrorConstants.NOT_CREATED,e.getCode());
			assertEquals(ErrorMessage.NOT_CREATED,e.getMessage());
		}
		
		
	}
	
	@Test
	public void test_updateContact_when_successful() throws GenericException{
		when(contactsRepository.findByIdAndUsername(4, "joe")).thenReturn(getPreDefinedTestContactObject());
		when(contactsRepository.save(getPreDefinedTestContactObject())).thenReturn(getPreDefinedTestContactObject());
		Contact contact = contactsService.updateContact(getPreDefinedTestContactObjectBeforeCreation(), 4L, "joe");
		assertEquals(getPreDefinedTestContactObjectBeforeCreation().getLastName(),contact.getLastName());
		assertNotNull(contact);
	}
	
	@Test
	public void test_updateContact_when_unsuccessful_contact_id_sent_doesnot_exist() {
		when(contactsRepository.findByIdAndUsername(4, "joe")).thenReturn(null);
		when(contactsRepository.save(getPreDefinedTestContactObject())).thenReturn(getPreDefinedTestContactObject());
		Contact contact;
		try {
			contact = contactsService.updateContact(getPreDefinedTestContactObjectBeforeCreation(), 4L, "joe");
		} catch (GenericException e) {
			assertEquals(ErrorConstants.EMPTY,e.getCode());
			assertEquals(ErrorMessage.EMPTY,e.getMessage());
			
		}
	
	}
	
	@Test
	public void test_updatePhone_when_successful() throws GenericException{
		when(contactsRepository.findByIdAndUsername(4, "joe")).thenReturn(getPreDefinedTestContactObject());
		when(phoneRepository.save(getPreDefinedTestPhoneObjectBeforeCreation())).thenReturn(getPreDefinedTestPhoneObject());
		Long id = contactsService.updatePhone(getPreDefinedTestPhoneObjectBeforeCreation(), 4L, "joe");
		assertEquals(4L,id.longValue());
	}
	
	@Test
	public void test_updatePhone_when_unsuccessful_contact_id_sent_doesnot_exist() {
		when(contactsRepository.findByIdAndUsername(4, "joe")).thenReturn(null);
		when(phoneRepository.save(getPreDefinedTestPhoneObjectBeforeCreation())).thenReturn(getPreDefinedTestPhoneObject());
		
		try {
			Long id = contactsService.updatePhone(getPreDefinedTestPhoneObjectBeforeCreation(), 4L, "joe");
		} catch (GenericException e) {
			assertEquals(ErrorConstants.EMPTY,e.getCode());
			assertEquals(ErrorMessage.EMPTY,e.getMessage());
			
		}
	
	}
	@Test
	public void test_updatePhone_when_unsuccessful_phone_already_exists_sent_doesnot_exist() {
		Contact contact = getPreDefinedTestContactObject();
		Set<Phone> phones = contact.getPhones();
		phones.add(getPreDefinedTestPhoneObject());
		when(contactsRepository.findByIdAndUsername(4, "joe")).thenReturn(contact);
		when(phoneRepository.save(getPreDefinedTestPhoneObjectBeforeCreation())).thenReturn(getPreDefinedTestPhoneObject());
		
		try {
			Long id = contactsService.updatePhone(getPreDefinedTestPhoneObjectBeforeCreation(), 4L, "joe");
		} catch (GenericException e) {
			assertEquals(ErrorConstants.PHONE_EXIST,e.getCode());
			assertEquals(ErrorMessage.PHONE_EXIST,e.getMessage());
			
		}
	
	}
	
	@Test
	public void test_updatePhone_when_unsuccessful_phone_number_not_sent_in_body() {
		Phone phone = getPreDefinedTestPhoneObject();
		phone.setPhoneNumber(null);
		when(contactsRepository.findByIdAndUsername(4, "joe")).thenReturn(getPreDefinedTestContactObject());
		when(phoneRepository.save(getPreDefinedTestPhoneObjectBeforeCreation())).thenReturn(phone);
		
		try {
			Long id = contactsService.updatePhone(getPreDefinedTestPhoneObjectBeforeCreation(), 4L, "joe");
		} catch (GenericException e) {
			assertEquals(ErrorConstants.PHONE_EMPTY,e.getCode());
			assertEquals(ErrorMessage.PHONE_EMPTY,e.getMessage());
			
		}
	
	}
	
	
	
	
	
	
	
	
	
	private Contact getPreDefinedTestContactObject(){
		Contact contact = new Contact();
		contact.setId(4L);
		contact.setFirstName("test first name");
		contact.setLastName("test last name");
		contact.setPhones(new HashSet<Phone>());
		return contact;
	}
	
	private Phone getPreDefinedTestPhoneObject(){
		Phone phone = new Phone();
		phone.setPhoneNumber("+1 (555) 123-456");
		phone.setContact(getPreDefinedTestContactObject());
		phone.setId(1);
		return phone;
	}
	private Phone getPreDefinedTestPhoneObjectBeforeCreation(){
		Phone phone = new Phone();
		phone.setPhoneNumber("+1 (555) 123-456");
		phone.setContact(getPreDefinedTestContactObject());
		return phone;
	}
	
	private Contact getPreDefinedTestContactObjectBeforeCreation(){
		Contact contact = new Contact();
		contact.setFirstName("test first name");
		contact.setLastName("test last name2");
		return contact;
	}
	private List<Contact> getPreDefinedListOfContactObject(){
		List<Contact> contactList = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setId(4L);
		contact.setFirstName("test first name1");
		contact.setLastName("test last name1");
		contactList.add(contact);
		contact = new Contact();
		contact.setId(5L);
		contact.setFirstName("test first name2");
		contact.setLastName("test last name2");
		contactList.add(contact);
		contact.setId(5L);
		contact.setFirstName("test first name3");
		contact.setLastName("test last name3");
		contactList.add(contact);
		return contactList;
		
	}
	
}

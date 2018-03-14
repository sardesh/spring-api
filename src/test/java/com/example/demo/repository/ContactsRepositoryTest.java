package com.example.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Contact;

/**
 * Integration test for repository
 * @author Sardesh Sharma
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@TestPropertySource(
  locations = "classpath:application-test.properties")
public class ContactsRepositoryTest {

	@Autowired
	private ContactsRepository contactsRepository;
	
	@Test
	public void test_findContactByUsername_result_success(){
		Contact contact = contactsRepository.save(getPreDefinedTestContactObject());
		
		List<Contact> contacts = contactsRepository.findContactByUsername("joe");
		assertEquals(contact, contacts.get(0));
	}
	
	@Test
	public void test_createConatct_result_success(){
		
		Contact contact = contactsRepository.save(getPreDefinedTestContactObject());
		assertNotNull(contact);
	}
	
	@Test
	public void test_findByIdAndUsername_result_success(){
		Contact contact = contactsRepository.save(getPreDefinedTestContactObject());
		
		Contact contactFound = contactsRepository.findByIdAndUsername(contact.getId(),"joe");
		assertEquals(contact, contactFound);
	}
	
	@Test
	public void test_delete_result_success(){
		Contact contact = contactsRepository.save(getPreDefinedTestContactObject());
		
		contactsRepository.delete(contact.getId());
		
		Contact contactFound = contactsRepository.findByIdAndUsername(contact.getId(),"joe");

		assertNull(contactFound);
	}
	
	@Test
	public void test_update_result_success(){
		Contact contact = contactsRepository.save(getPreDefinedTestContactObject());
		Contact contactFound = contactsRepository.findByIdAndUsername(contact.getId(),"joe");
		contactFound.setFirstName("test updated first name");
		contactFound.setLastName("test updated last name");
		Contact contactUpdated = contactsRepository.save(contactFound);
		List<Contact> contactFoundAfterUpdate = contactsRepository.findContactByUsername("joe");
		
		assertEquals(1, contactFoundAfterUpdate.size());
		assertEquals(contactUpdated, contactFoundAfterUpdate.get(0));
	}
	
	
	private Contact getPreDefinedTestContactObject(){
		Contact contact = new Contact();
		
		contact.setFirstName("test first name");
		contact.setLastName("test last name");
		contact.setUsername("joe");
		return contact;
	}
}

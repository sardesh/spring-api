package com.example.demo.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Contact;
import com.example.demo.model.Phone;

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
public class PhoneRepositoryTest {

	
	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	@Test
	public void test_update_phone_to_contact(){
		Contact contact = contactsRepository.save(getPreDefinedTestContactObject());
		Phone phone = new Phone();
		Contact contactFound = contactsRepository.findByIdAndUsername(contact.getId(),"joe");
		phone.setContact(contactFound);
		phone.setPhoneNumber("+1 (555) 123-456");
		Phone phoneSaved = phoneRepository.save(phone);
		assertEquals(1, phoneSaved.getId());
		assertEquals(contactFound.getId(), phoneSaved.getContact().getId());
	
		
	}
	
	
	private Contact getPreDefinedTestContactObject(){
		Contact contact = new Contact();
		
		contact.setFirstName("test first name");
		contact.setLastName("test last name");
		contact.setUsername("joe");
		return contact;
	}

}




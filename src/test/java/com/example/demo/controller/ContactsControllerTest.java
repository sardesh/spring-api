package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.model.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test for controller
 * @author Sardesh Sharma
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-test.properties")
public class ContactsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * Secured url test without credentials for unauthorized status
	 * @throws Exception
	 */
	@Test
	public void test_get_all_contacts_unauthorized() throws Exception{
		
		mockMvc.perform(get("/contacts"))
		.andExpect(status().isUnauthorized());
		
	}
	
	/**
	 * Secured url test without credentials for unauthorized status
	 * @throws Exception
	 */
	@Test
	public void test_save_contact_when_unauthorized() throws Exception{
		
		mockMvc.perform(post("/contacts").content("{\"first_name\": \"test first name\",\"last_name\": \"test last name\"}"))
		.andExpect(status().isUnauthorized());
		
	}
	/**
	 * Secured url test without credentials for unauthorized status
	 * @throws Exception
	 */
	@Test
	public void test_update_contacts_when_unauthorized() throws Exception{
		
		mockMvc.perform(get("/contacts"))
		.andExpect(status().isUnauthorized());
		
	}
	
	/**
	 * Secured url test without credentials for unauthorized status
	 * @throws Exception
	 */
	@Test
	public void test_add_phone_to_contact_when_unauthorized() throws Exception{
		
		mockMvc.perform(post("/contacts/{id}/entries",4))
		.andExpect(status().isUnauthorized());
		
	}
	
	/**
	 * Secured url test without credentials for unauthorized status
	 * @throws Exception
	 */
	@Test
	public void test_delete_contact_when_unauthorized() throws Exception{
		
		mockMvc.perform(delete("/contacts/{id}",4))
		.andExpect(status().isUnauthorized());
		
	}
	
	/**
	 * Secured url test with credentials for fetching contacts for authenticated user with desired output
	 * @throws Exception
	 */
	@Test
	public void test_fetchAllContact_when_authorized() throws Exception{
		 mockMvc.perform(post("/contacts").content("{\"first_name\": \"test first name\",\"last_name\": \"test last name\"}")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.with(httpBasic("joe", "password")))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.id").isNotEmpty())
					.andExpect(content()
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		 
		mockMvc.perform(get("/contacts").with(httpBasic("joe", "password")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		
	}
	 
	/**
	 * Secured url test with credentials for created status and desired output
	 * @throws Exception
	 */
	@Test
	public void test_save_contact_when_authorized() throws Exception{
		
		mockMvc.perform(post("/contacts").content("{\"first_name\": \"test first name\",\"last_name\": \"test last name\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("joe", "password")))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		
		
	}
	

	/**
	 * Secured url test with credentials for updating contacts with desired output
	 * @throws Exception
	 */
	@Test
	public void test_update_contacts_when_authorized() throws Exception{
		MvcResult result = mockMvc.perform(post("/contacts").content("{\"first_name\": \"test first name\",\"last_name\": \"test last name\"}")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.with(httpBasic("joe", "password")))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.id").isNotEmpty())
					.andExpect(content()
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
		String jSON = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		Contact contact = mapper.readValue(jSON, Contact.class);
		mockMvc.perform(post("/contacts/{id}",contact.getId()).content("{\"first_name\": \"test updated first name\",\"last_name\": \"test updated last name\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.with(httpBasic("joe", "password")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id",is(contact.getId().intValue())));
		
	}
	/**
	 * Secured url test with credentials for updating phone with desired output
	 * @throws Exception
	 */
	
	@Test
	public void test_add_phone_to_contact_when_authorized() throws Exception{
		MvcResult result = mockMvc.perform(post("/contacts").content("{\"first_name\": \"test first name\",\"last_name\": \"test last name\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("joe", "password")))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
			String jSON = result.getResponse().getContentAsString();
			ObjectMapper mapper = new ObjectMapper();
			Contact contact = mapper.readValue(jSON, Contact.class);
		
		mockMvc.perform(post("/contacts/{id}/entries",contact.getId()).content("{\"phone\": \"+1 (555) 123-456\" }")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.with(httpBasic("joe","password")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	
	
	/**
	 * Secured url test with credentials for deleteing contact with desired output status
	 * @throws Exception
	 */
	@Test
	public void test_delete_contact_when_authorized() throws Exception{
		MvcResult result = mockMvc.perform(post("/contacts").content("{\"first_name\": \"test first name\",\"last_name\": \"test last name\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("joe", "password")))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
			String jSON = result.getResponse().getContentAsString();
			ObjectMapper mapper = new ObjectMapper();
			Contact contact = mapper.readValue(jSON, Contact.class);
			mockMvc.perform(delete("/contacts/{id}",contact.getId()).with(httpBasic("joe","password")))
			.andExpect(status().isOk());
			mockMvc.perform(delete("/contacts/{id}",contact.getId()).with(httpBasic("joe","password")))
			.andExpect(status().isNoContent());
		
	}
	

	

}

package com.example.demo.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.demo.Dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-test.properties")
public class UserControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void test_create_user_success() throws Exception{
		mockMvc.perform(get("/contacts").with(httpBasic("testuser", "testpassword")))
		.andExpect(status().isUnauthorized());
		mockMvc.perform(post("/users/register").content("{\"username\": \"testuser\",\"password\": \"testpassword\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/contacts").with(httpBasic("testuser", "testpassword")))
		.andExpect(status().isNoContent());
		

	}
	
	@Test
	public void test_createToken_user_success() throws Exception{
		mockMvc.perform(get("/contacts").with(httpBasic("testuser1", "testpassword1")))
		.andExpect(status().isUnauthorized());
		mockMvc.perform(post("/users/register").content("{\"username\": \"testuser1\",\"password\": \"testpassword1\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		MvcResult response = mockMvc.perform(post("/users")
				.content("{\"username\": \"testuser1\",\"password\": \"testpassword1\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.token").isNotEmpty()).andReturn();
		String jSON = response.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		TokenDto dto = mapper.readValue(jSON, TokenDto.class);
		mockMvc.perform(get("/contacts").header("Authorization", "Basic "+dto.getToken()))
		.andExpect(status().isNoContent());
		

	}
}

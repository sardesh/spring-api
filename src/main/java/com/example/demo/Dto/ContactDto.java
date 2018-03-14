package com.example.demo.Dto;

import java.util.Arrays;

/**
 * Data transfer object for Contacts
 * @author Sardesh Sharma
 *
 */
public class ContactDto {

			private Long id;
			
			private String first_name;
			
			private String last_name;
			
			private String[] phones;

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getFirst_name() {
				return first_name;
			}

			public void setFirst_name(String first_name) {
				this.first_name = first_name;
			}

			public String getLast_name() {
				return last_name;
			}

			public void setLast_name(String last_name) {
				this.last_name = last_name;
			}

			public String[] getPhones() {
				return phones;
			}

			public void setPhones(String[] phones) {
				this.phones = phones;
			}

			public ContactDto(Long id, String first_name, String last_name, String[] phones) {
				super();
				this.id = id;
				this.first_name = first_name;
				this.last_name = last_name;
				this.phones = phones;
			}

			@Override
			public String toString() {
				return "ContactDto [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", phones="
						+ Arrays.toString(phones) + "]";
			}
			
			
			
			
}

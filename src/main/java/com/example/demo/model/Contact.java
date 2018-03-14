package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Model for Contact
 * @author Sardesh Sharma
 *
 */

@Entity
@Table(name = "contacts")
@JsonInclude(value=Include.NON_NULL)
public class Contact {
		
		@Id
		@Column(name="id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)	
		private Long id;
		
		@NotEmpty(message="Please enter your first name")
		@Column(name="first_name")
		private String firstName;
		
		
		@Column(name="last_name")
		private String lastName;
		
		@OneToMany(mappedBy="contact",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
		private Set<Phone> phones;

		@Column(name="username")
		@JsonIgnore
		private String username;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Set<Phone> getPhones() {
			return phones;
		}

		public void setPhones(Set<Phone> phones) {
			this.phones = phones;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Contact other = (Contact) obj;
			if (firstName == null) {
				if (other.firstName != null)
					return false;
			} else if (!firstName.equals(other.firstName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phones=" + phones
					+ ", username=" + username + "]";
		}
		

		

		
		
		
		
}

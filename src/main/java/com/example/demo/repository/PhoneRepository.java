package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Phone;

/**
 * 
 * Repository/ Dao layer for performing CRUD operation on phone model
 * 
 * @author Sardesh Sharma
 *
 */
public interface PhoneRepository extends JpaRepository<Phone, String> {

}

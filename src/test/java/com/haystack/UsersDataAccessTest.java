package com.haystack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.User;

public class UsersDataAccessTest {
	
	UserJDBCTemplate userJDBCTemplate;
	
	@Before
	public void setUp() {
		userJDBCTemplate = new UserJDBCTemplate();
	}
	
	/*@Test
	public void createUsers() {
	    userJDBCTemplate.create("user7", "test", "example7@example.com");
	    userJDBCTemplate.create("user8", "test", "example8@example.com");
	    userJDBCTemplate.create("user9", "test", "example9@example.com");
	}*/
	
	/*@Test
	public void deleteUsers() {
	    userJDBCTemplate.delete(7);
	    userJDBCTemplate.delete(8);
	    userJDBCTemplate.delete(9);
	}*/
	
	/*@Test
	public void updateUsers() {
		userJDBCTemplate.updateEmail(2, "matthewwildman@hotmail.com");
	}*/
	
	@Test
	public void testUsers() {
		User matt = userJDBCTemplate.getById(1);
		String testEmail = matt.getEmail();
		assertEquals("Email not updated correctly", "matthewwildman@gmail.com", testEmail);
	}
	
}

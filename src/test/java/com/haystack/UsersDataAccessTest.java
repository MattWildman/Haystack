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
	
	@Test
	public void createUsers() {
	    userJDBCTemplate.create("user1", "test", "example1@example.com");
	    userJDBCTemplate.create("user2", "test", "example2@example.com");
	    userJDBCTemplate.create("user3", "test", "example3@example.com");
	    userJDBCTemplate.create("user4", "test", "example4@example.com");
	    userJDBCTemplate.create("user5", "test", "example5@example.com");
	    userJDBCTemplate.create("user6", "test", "example6@example.com");
	    userJDBCTemplate.create("user7", "test", "example7@example.com");
	    userJDBCTemplate.create("user8", "test", "example8@example.com");
	    userJDBCTemplate.create("user9", "test", "example9@example.com");
	}
	
	@Test
	public void deleteUsers() {
	    userJDBCTemplate.delete(7);
	    userJDBCTemplate.delete(8);
	    userJDBCTemplate.delete(9);
	}
	
	@Test
	public void updateUsers() {
		userJDBCTemplate.updateEmail(1, "matthewwildman@gmail.com");
	}
	
	@Test
	public void testUsers() {
		User matt = userJDBCTemplate.getUser(1);
		String testEmail = matt.getEmail();
		assertEquals("Email not updated correctly", "matthewwildman@gmail.com", testEmail);
	}
	
}

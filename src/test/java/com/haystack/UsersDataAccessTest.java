package com.haystack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.User;

public class UsersDataAccessTest {
	
	UserJDBCTemplate userJDBCTemplate;
	User user;
	
	@Before
	public void setUp() {
		userJDBCTemplate = new UserJDBCTemplate();
		user = new User();
		user.setUsername("KeyTest2");
		user.setPassword("test");
		user.setEmail("testkey2@testkey.com");
	}
	
	@Test
	public void createUsers() {
	    userJDBCTemplate.save("user7", "test", "example7@example.com");
	    userJDBCTemplate.save("user8", "test", "example8@example.com");
	    userJDBCTemplate.save("user9", "test", "example9@example.com");
	}
	
	@Test
	public void deleteUsers() {
	    userJDBCTemplate.delete(7);
	    userJDBCTemplate.delete(8);
	    userJDBCTemplate.delete(9);
	}
	
	@Test
	public void updateUsers() {
		userJDBCTemplate.updateEmail(2, "matthewwildman@hotmail.com");
	}
	
	@Test
	public void testUsers() {
		User matt = userJDBCTemplate.getById(1);
		String testEmail = matt.getEmail();
		assertEquals("Email not updated correctly", "matthewwildman@gmail.com", testEmail);
	}
	
	@Test 
	public void testInsert() {
		Number key = userJDBCTemplate.saveAndReturnKey(user, 1);
		System.out.println("the new user's key is: " + key);
	}
	
}

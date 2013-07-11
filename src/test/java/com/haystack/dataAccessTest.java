package com.haystack;

import org.junit.Before;
import org.junit.Test;

import com.haystack.UserJDBCTemplate;

public class dataAccessTest {
	
	UserJDBCTemplate userJDBCTemplate;
	
	@Before
	public void setUp() {
		this.userJDBCTemplate = new UserJDBCTemplate();
	}
	
	@Test
	public void createUsers() {
	    userJDBCTemplate.create("Test4", "User4", "example4@example.com");
	    userJDBCTemplate.create("Test5", "User5", "example5@example.com");
	    userJDBCTemplate.create("Test6", "User6", "example6@example.com");
	}
	
	@Test
	public void createMoreUsers() {
	    userJDBCTemplate.create("Test7", "User7", "example7@example.com");
	    userJDBCTemplate.create("Test8", "User8", "example8@example.com");
	    userJDBCTemplate.create("Test9", "User9", "example9@example.com");
	}
	
	@Test
	public void deleteUsers() {
	    userJDBCTemplate.delete(7);
	    userJDBCTemplate.delete(8);
	    userJDBCTemplate.delete(9);
	}
	
}

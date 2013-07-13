package com.haystack;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {
	
	public void setDataSource(DataSource dataSource);
	
	public void create(	String firstName, String lastName, String userName,
						String password, String email, Character receiveEmails);
	
	public User getUser(Integer uid);
	
	public List<User> listUsers();
	
	public void delete(Integer uid);
	
	public void updateFirstName(Integer uid, String firstName);
	
	public void updateLastName(Integer uid, String lastName);
	
	public void updateEmail(Integer uid, String email);

}

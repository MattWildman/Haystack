package com.haystack;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {
	
	public void setDataSource(DataSource dataSource);
	
	public void create(	String firstName, String lastName, String userName,
						String password, String email);
	
	public User getUser(Integer id);
	
	public List<User> listUsers();
	
	public void delete(Integer id);
	
	public void updateFirstName(Integer id, String firstName);
	
	public void updateLastName(Integer id, String lastName);
	
	public void updateUsername(Integer id, String username);
	
	public void updatePassword(Integer id, String password);
	
	public void updateEmail(Integer id, String email);

}

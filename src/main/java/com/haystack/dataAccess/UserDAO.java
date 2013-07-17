package com.haystack.dataAccess;

import java.util.List;

import javax.sql.DataSource;

import com.haystack.entities.User;

public interface UserDAO {
	
	public void setDataSource(DataSource dataSource);
	
	public void create(String userName, String password, String email);
	
	public User getUser(Integer id);
	
	public List<User> listUsers();
	
	public void delete(Integer id);
	
	public void updateUsername(Integer id, String username);
	
	public void updatePassword(Integer id, String password);
	
	public void updateEmail(Integer id, String email);
	
	public void updateEnabled(Integer id, Integer enabled);

}

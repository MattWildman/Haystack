package com.haystack;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJDBCTemplate implements UserDAO {

	private JdbcTemplate jdbcTemplateObject;
	
	public UserJDBCTemplate() {		   
		jdbcTemplateObject = new JdbcTemplate(new HaystackDataSource());
	}
	
	public void setDataSource(DataSource dataSource) {}

	public void create(String firstName, String lastName, String email) {
		String SQL = "INSERT INTO users (firstname, lastname, email) VALUES (?, ?, ?)";
		jdbcTemplateObject.update(SQL, firstName, lastName, email);
 	}

 	public User getUser(Integer uid) {
 		String SQL = "select * from users where uid = ?";
 		User user = jdbcTemplateObject.queryForObject(SQL, new Object[]{uid}, new UserMapper());
 		return user;
 	}

 	public List<User> listUsers() {
 		String SQL = "select * from users";
 		List <User> users = jdbcTemplateObject.query(SQL, new UserMapper());
 		return users;
 	}

 	public void delete(Integer uid) {
 		String SQL = "DELETE FROM users WHERE uid = ?";
 		jdbcTemplateObject.update(SQL, uid);
 	}

	public void updateFirstName(Integer uid, String firstName) {
		String SQL = "update users set firstname = ? where uid = ?";
		jdbcTemplateObject.update(SQL, firstName, uid);
	}

	public void updateLastName(Integer uid, String lastName) {
		String SQL = "update users set lasttname = ? where uid = ?";
		jdbcTemplateObject.update(SQL, lastName, uid);
	}

	public void updateEmail(Integer uid, String email) {
		String SQL = "update users set email = ? where uid = ?";
		jdbcTemplateObject.update(SQL, email, uid);
	}
	
}

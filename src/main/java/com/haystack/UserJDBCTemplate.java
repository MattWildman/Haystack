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

	public void create(	String firstName, String lastName, String userName,
						String password, String email, Character receiveEmails) {
		String SQL = "INSERT INTO users (firstName, lastName, userName, password, email, receiveEmails) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, firstName, lastName, userName, password, email, receiveEmails);
 	}

 	public User getUser(Integer id) {
 		String SQL = "SELECT * FROM users WHERE id = ?";
 		User user = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new UserMapper());
 		return user;
 	}

 	public List<User> listUsers() {
 		String SQL = "SELECT * FROM users";
 		List <User> users = jdbcTemplateObject.query(SQL, new UserMapper());
 		return users;
 	}

 	public void delete(Integer id) {
 		String SQL = "DELETE FROM users WHERE id = ?";
 		jdbcTemplateObject.update(SQL, id);
 	}

	public void updateFirstName(Integer id, String firstName) {
		String SQL = "UPDATE users set firstName = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, firstName, id);
	}

	public void updateLastName(Integer id, String lastName) {
		String SQL = "UPDATE users set lasttName = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, lastName, id);
	}

	public void updateEmail(Integer id, String email) {
		String SQL = "UPDATE users set email = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, email, id);
	}
	
}

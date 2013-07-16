package com.haystack;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJDBCTemplate implements UserDAO {
	
	private DataSource dataSource = new HaystackDataSource();
	private JdbcTemplate jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(	String firstName, String lastName, String username,
						String password, String email) {
		String SQL = "INSERT INTO users (firstName, lastName, username, password, email) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, firstName, lastName, username, password, email);
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
		String SQL = "UPDATE users set lastName = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, lastName, id);
	}
	
	public void updateUsername(Integer id, String username) {
		String SQL = "UPDATE users set username = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, username, id);
	}

	public void updatePassword(Integer id, String password) {
		String SQL = "UPDATE users set password = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, password, id);
	}

	public void updateEmail(Integer id, String email) {
		String SQL = "UPDATE users set email = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, email, id);
	}
	
}

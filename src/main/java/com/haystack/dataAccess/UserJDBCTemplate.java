package com.haystack.dataAccess;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.haystack.entities.User;

@Repository
public class UserJDBCTemplate extends HaystackDAO<User> {
	
	public UserJDBCTemplate() {
		this.setTableName("users");
		this.setRowMapper(new UserMapper());
	}

	public void updateUsername(Integer id, String username) {
		this.update(id, "username", username);
	}

	public void updatePassword(Integer id, String password) {
		this.update(id, "password", password);

	}

	public void updateEmail(Integer id, String email) {
		this.update(id, "email", email);

	}
	
	public void updateEnabled(Integer id, Integer enabled) {
		enabled = enabled >= 1 ? 1 : 0;
		this.update(id, "enabled", enabled);
	}
	
	public User getByUsername(String username) {
		String SQL = "SELECT * FROM users WHERE username = ?";
 		User result = jdbcTemplateObject.queryForObject(SQL, new Object[]{username}, this.getRowMapper());
 		return result;
	}
	
	public User getByEmail(String email) {
		String SQL = "SELECT * FROM users WHERE username = ?";
 		User result = jdbcTemplateObject.queryForObject(SQL, new Object[]{email}, this.getRowMapper());
 		return result;
	}
	
	public User getByConnectionId(Integer conId) {
		String SQL = "SELECT * FROM users WHERE id IN (SELECT c.userId FROM connections c WHERE c.id = ?)";
		User result = jdbcTemplateObject.queryForObject(SQL, new Object[]{conId}, this.getRowMapper());
		return result;
	}
	
	public List<User> getContacts(Integer userId) {
		String SQL = "SELECT * " +
					 "FROM users " +
					 "WHERE id IN " +
					 "	(SELECT permittedId " +
					 "	 FROM messagepermissionsview " +
					 "	 WHERE userId = ?)";
		
		List<User> results = jdbcTemplateObject.query(SQL, new Object[]{userId}, 
													  this.getRowMapper());
		return results;
	}
	
	public List<User> getBlockedContacts(Integer userId) {
		String SQL = "SELECT * " +
					 "FROM users " +
					 "WHERE id IN " +
					 "	(SELECT blockedId " +
					 "	 FROM blockings " +
					 "	 WHERE userId = ?)";
		
		List<User> results = jdbcTemplateObject.query(SQL, new Object[]{userId}, 
				  									  this.getRowMapper());
		return results;
	}

	@Override
	public void save(User user, Integer ownerId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (username, password, email) VALUES (?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, user.getUsername(), user.getPassword(), user.getEmail());
		//MySQL trigger then adds new username to authorites table
	}
	
	public void save(String username, String password, String email) {
		String SQL = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, username, password, email);
		//MySQL trigger then adds new username to authorites table
 	}
	
	@Override
	public Integer saveAndReturnKey(User user, Integer ownerId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", user.getUsername());
		parameters.addValue("password", user.getPassword());
		parameters.addValue("email", user.getEmail());
		parameters.addValue("enabled", 1);
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		//MySQL trigger then adds new username to authorites table
		return longKey.intValue();
 	}
	
}

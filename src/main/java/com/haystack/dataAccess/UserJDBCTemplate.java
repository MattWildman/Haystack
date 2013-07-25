package com.haystack.dataAccess;

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

	@Override
	public void save(User user, Integer ownerId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (username, password, email) VALUES (?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, user.getUsername(), user.getPassword(), user.getEmail());
		//MySQL trigger then adds new username to authorites table
	}
	
	public void create(String username, String password, String email) {
		String SQL = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, username, password, email);
		//MySQL trigger then adds new username to authorites table
 	}
	
}

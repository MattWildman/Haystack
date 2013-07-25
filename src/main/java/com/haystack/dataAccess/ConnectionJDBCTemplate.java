package com.haystack.dataAccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.haystack.entities.Connection;

@Repository
public class ConnectionJDBCTemplate extends HaystackDAO<Connection> {

	public ConnectionJDBCTemplate() {
		this.setTableName("connections");
		this.setRowMapper(new ConnectionMapper());
	}
	
	public void updateConType(Integer id, String conType) {
		this.update(id, "conType", conType);
	}

	
	public void updateStatus(Integer id, Integer status) {
		this.update(id, "status", status);
	}
	
	public List<Connection> getUserConnections(Integer userId) {
		return this.getByOwnerId("userId", userId);
	}
	
	@Override
	public void save(Connection c, Integer userId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (userId, title, summary, conType, status) VALUES (?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, userId, c.getTitle(), c.getSummary(), c.getConType(), c.getStatus());
	}
	
}

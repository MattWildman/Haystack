package com.haystack.dataAccess;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public abstract class HaystackDAO<T> {

	private String tableName;
	private RowMapper<T> rowMapper;
	
	protected DataSource dataSource = new HaystackDataSource();
	protected JdbcTemplate jdbcTemplateObject = new JdbcTemplate(this.getDataSource());
	
	protected String getTableName() {
		return tableName;
	}

	protected void setTableName(String tableName) {
		this.tableName = tableName;
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	protected void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	protected RowMapper<T> getRowMapper() {
		return rowMapper;
	}

	protected void setRowMapper(RowMapper<T> rowMapper) {
		this.rowMapper = rowMapper;
	}
	
	public abstract void create(T o, Integer ownerId);
	
	public List<T> listAll() {
		String SQL = "SELECT * FROM " + this.getTableName();
 		List <T> results = jdbcTemplateObject.query(SQL, this.getRowMapper());
 		return results;
	}
	
	public List<T> getByOwnerId(String ownerIdCol, Integer ownerId) {
		String SQL = "SELECT * FROM " + this.getTableName() + " WHERE " + ownerIdCol + " = ?";
 		List <T> results = jdbcTemplateObject.query(SQL, new Object[]{ownerId}, this.getRowMapper());
 		return results;
	}
	
	public T getById(Integer id) {
		String SQL = "SELECT * FROM " + this.getTableName() + " WHERE id = ?";
 		T result = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, this.getRowMapper());
 		return result;
	}
	
	public void delete(Integer id) {
		String SQL = "DELETE FROM " + this.getTableName() + " WHERE id = ?";
 		jdbcTemplateObject.update(SQL, id);
	}
	
	public void update(Integer id, String column, Object value) {
		String SQL = "UPDATE " + this.getTableName() + " SET " + column + " = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, value, id);
	}
	
	public void updateTitle(Integer id, String title) {
		this.update(id, "title", title);
	}

	public void updateSummary(Integer id, String summary) {
		this.update(id, "summary", summary);
	}
	
}

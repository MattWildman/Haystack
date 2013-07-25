package com.haystack.dataAccess;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.haystack.entities.Context;

@Repository
public class ContextJDBCTemplate extends HaystackDAO<Context> {

	public ContextJDBCTemplate() {
		this.setTableName("contexts");
		this.setRowMapper(new ContextMapper());
	}
	
	public void updateEarliest(Integer id, Date earliest) {
		this.update(id, "earliest", earliest);
	}
	
	public void updateLatest(Integer id, Date latest) {
		this.update(id, "latest", latest);
	}
	
	public List<Context> getConnectionContexts(Integer conId) {
		return this.getByOwnerId("conId", conId);
	}
	
	@Override
	public void save(Context cx, Integer conId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (conId, title, summary, earliest, latest, locationType) VALUES (?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, conId, cx.getTitle(), cx.getSummary(), 
									   cx.getEarliest(), cx.getLatest(), cx.getLocationType());
	}
	
}

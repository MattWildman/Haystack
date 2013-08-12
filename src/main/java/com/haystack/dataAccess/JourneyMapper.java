package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Journey;

@Service
public class JourneyMapper implements RowMapper<Journey> {
	
	private LocationJDBCTemplate locationJDBCTemplate = new LocationJDBCTemplate();
	
	private String buildTitle(Journey j) {
		String type = j.getType() + " journey";
		String company = j.getCompany().isEmpty() ? "" : " - " + j.getCompany();
		String service = j.getService().isEmpty() ? "" : " - " + j.getService();
		return type + company + service;
	}
	
	public Journey mapRow(ResultSet rs, int rowNum) throws SQLException {
		Journey journey = new Journey();
		journey.setId(rs.getInt("id"));
		journey.setSummary(rs.getString("description"));
		journey.setType(rs.getString("type"));
		journey.setCompany(rs.getString("company"));
		journey.setService(rs.getString("service"));
		journey.setTitle(this.buildTitle(journey));
		journey.setStart(locationJDBCTemplate.getById(rs.getInt("startId")));
		journey.setEnd(locationJDBCTemplate.getById(rs.getInt("endId")));
		return journey;
	}

}

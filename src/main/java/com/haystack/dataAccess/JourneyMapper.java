package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Journey;

@Service
public class JourneyMapper implements RowMapper<Journey> {
	
   public Journey mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Journey journey = new Journey();
      journey.setId(rs.getInt("id"));
      journey.setSummary(rs.getString("description"));
      journey.setType(rs.getString("type"));
      journey.setCompany(rs.getString("company"));
      journey.setService(rs.getString("service"));
      journey.setTitle(buildTitle(journey));
      //TODO set start and end locations
      return journey;
   }

	private String buildTitle(Journey j) {
		String type = j.getType();
		String company = j.getCompany().isEmpty() ? " - " + j.getCompany() : "";
		String service = j.getService().isEmpty() ? " - " + j.getService() : "";
		return type + company + service;
	}

}

package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Participant;

@Service
public class ParticipantMapper implements RowMapper<Participant> {
	
	public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Participant participant = new Participant();
		participant.setId(rs.getInt("id"));
		participant.setTitle(rs.getString("title"));
		participant.setSummary(rs.getString("description"));
		participant.setGender(rs.getString("gender"));
		participant.setMinAge(rs.getInt("minAge"));
		participant.setMaxAge(rs.getInt("maxAge"));
		participant.setHeight(rs.getInt("height"));
		return participant;
	}

}

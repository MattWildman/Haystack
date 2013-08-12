package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Context;

@Service
public class ContextMapper implements RowMapper<Context> {
	
	private Date setDate(Date date, Time time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute = calendar.get(Calendar.MINUTE);
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	public Context mapRow(ResultSet rs, int rowNum) throws SQLException {
		Context context = new Context();
		context.setId(rs.getInt("id"));
		context.setEarliest(this.setDate(rs.getDate("earliest"), rs.getTime("earliest")));
		context.setLatest(this.setDate(rs.getDate("latest"), rs.getTime("latest")));
		context.setLocationType(rs.getString("locationType"));
		return context;
	}
   
}

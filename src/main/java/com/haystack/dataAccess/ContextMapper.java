package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Context;

@Service
public class ContextMapper implements RowMapper<Context> {
	
   public Context mapRow(ResultSet rs, int rowNum) throws SQLException {
      Context context = new Context();
      context.setId(rs.getInt("id"));
      context.setEarliest(rs.getDate("earliest"));
      context.setLatest(rs.getDate("latest"));
      context.setLocationType(rs.getString("locationType"));
      return context;
   }
   
}

package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Connection;

@Service
public class ConnectionMapper implements RowMapper<Connection> {
	
   public Connection mapRow(ResultSet rs, int rowNum) throws SQLException {
      Connection connection = new Connection();
      connection.setId(rs.getInt("id"));
      connection.setTitle(rs.getString("title"));
      connection.setSummary(rs.getString("summary"));
      connection.setConType(rs.getString("conType"));
      connection.setStatus(rs.getString("status"));
      return connection;
   }
   
}

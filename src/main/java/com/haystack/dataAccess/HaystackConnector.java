package com.haystack.dataAccess;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.haystack.entities.Connection;
import com.haystack.entities.Meeting;
import com.haystack.entities.User;

public class HaystackConnector extends ConnectionJDBCTemplate {
	
	private static HaystackConnector instance = null;
	
	private HaystackDBFacade hdbf = new HaystackDBFacade();
	private UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
	
	protected HaystackConnector() {}
	
	public static HaystackConnector getInstance() {
		if (instance == null) {
			instance = new HaystackConnector();
		}
		return instance;
	}

	public Boolean checkForSharedConnection(Integer userConId, Integer candConId) {
		
		String SQL = "SELECT * " +
					 "FROM connections c " +
					 "WHERE c.id = " +
					 "	 (SELECT cd.userConId" +
					 "	  FROM candidates cd " +
					 "	  WHERE cd.status = 'accepted' " +
					 "	  AND cd.candConId = ? " +
					 "	  AND cd.userConId = ?)";
		
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {userConId, candConId}, 
								   this.getRowMapper());
		
		if (!results.isEmpty()) {
			
			Meeting meeting1 = hdbf.buildMeeting(this.getById(userConId));
			User user1 = userJDBCTemplate.getByConnectionId(userConId);
			
			for (Connection c : results) {
				Meeting meeting2 = hdbf.buildMeeting(c);
				User user2 = userJDBCTemplate.getByConnectionId(c.getId());
				HaystackMessenger.getInstance()
								 .sendSharedConnectionMessage(user1.getId(), meeting1, user2);
				HaystackMessenger.getInstance()
				 				 .sendSharedConnectionMessage(user2.getId(), meeting2, user1);
			}
			
			return true;
		}
		
		return false;
	}
	
	public List<Connection> getSharedConnections(Integer userId) {
		
		String SQL = "SELECT * FROM connections " +
					 "WHERE userId = ? " +
					 "AND id IN (SELECT conId1 FROM sharedconnectionsview)";
		
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {userId}, 
								   this.getRowMapper());
		
		return results;
		
	}
	
	public List<Connection> getCorrespondingSharedConnections(Integer meetingId) {
		
		Integer conId = hdbf.getConnectionId(meetingId);
		
		String SQL = "SELECT * FROM connections " +
					 "WHERE id IN" +
					 "	(SELECT conId2 FROM sharedconnectionsview " +
					 "	 WHERE conId1 = ?)";
	
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {conId}, 
								   this.getRowMapper());

		return results;
		
	}
	
	public Boolean areConnected(Integer meetingId, Integer candId) {
		
		Integer connectionId = hdbf.getConnectionId(meetingId);
		Integer candConId = hdbf.getConnectionId(candId);
		
		String SQL = "SELECT * FROM sharedconnectionsview " +
					 "WHERE conId1 = ? " +
					 "AND conId2 = ?";
		
		SqlRowSet srs = jdbcTemplateObject.queryForRowSet(SQL, 
				  new Object[] {connectionId, candConId});
		
		return srs.first();
	}

}

package com.haystack.dataAccess;

import java.util.List;

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
	
	public void updateSharedConnections(Integer userConId, Integer candConId) {
		
		String SQL = "INSERT INTO sharedconnections (conId1, conId2) " +
					 "SELECT cd.userConId, cd.candConId " +
					 "FROM candidates cd " +
					 "WHERE status = 'accepted' " +
					 "AND cd.userConId = ? " +
					 "AND cd.candConId = ? " +
					 "AND EXISTS " +
					 "	(SELECT * " +
					 "	 FROM candidates cd1 " +
					 "   WHERE status = 'accepted' " +
					 "   AND cd1.userConId = ? " +
					 "   AND cd1.candConId = ?)";
		
		jdbcTemplateObject.update(SQL, userConId, candConId, 
								  candConId, userConId);
		
		//MySQL trigger updates both connection statuses to 'matched'
		//MySQL trigger adds userIds to messagepermissions table
		
	}

	public void checkForSharedConnection(Integer userConId, Integer candConId) {
		
		String SQL1 = "SELECT * " +
					  "FROM connections c " +
					  "WHERE status = 'matched' " +
					  "AND (c.id = ? OR c.id = ?)";
		
		List<Connection> results = jdbcTemplateObject.query(SQL1, 
				   new Object[] {userConId, candConId}, 
				   this.getRowMapper());
		
		if (!results.isEmpty()) {
			Meeting meeting1 = hdbf.buildMeeting(results.get(0));
			Meeting meeting2 = hdbf.buildMeeting(results.get(1));
			User user1 = userJDBCTemplate.getByConnectionId(results.get(0).getId());
			User user2 = userJDBCTemplate.getByConnectionId(results.get(1).getId());
			HaystackMessenger.getInstance()
							 .sendSharedConnectionMessage(user1.getId(), meeting1, user2);
			HaystackMessenger.getInstance()
			 				 .sendSharedConnectionMessage(user2.getId(), meeting2, user1);
			
		}
		
	}
	
	

}

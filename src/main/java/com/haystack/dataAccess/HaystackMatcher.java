package com.haystack.dataAccess;

import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.haystack.entities.Connection;
import com.haystack.entities.Context;
import com.haystack.entities.Meeting;

public class HaystackMatcher extends ConnectionJDBCTemplate {
	
	private static HaystackMatcher instance = null;
	
	private HaystackDBFacade hdbf = new HaystackDBFacade();
	
	private void addCorrespondingCandidates(Integer userId, Integer userConId) {
		
		String SQL1 = "insert into candidates (userId, userConId, candConId) " +
					  "values (?, ?, ?)";
		
		String SQL2 = "select * " +
					  "from connections c " +
					  "where c.id in " +
					  "  (select cd.candConId " +
					  "   from candidates cd " +
					  "   where cd.userId = ? " +
					  "   and cd.userConId = ?) " +
					  "and c.id not in " +
					  "  (select cd.userConId " +
					  "   from candidates cd " +
					  "   where cd.candConId = ?)";
		
		List<Connection> results = jdbcTemplateObject.query(SQL2, 
								   new Object[] {userId, userConId, userConId}, 
								   this.getRowMapper());
		
		for (Connection c : results) {
			Integer toId = c.getUserId();
			Integer toConId = c.getId();
			jdbcTemplateObject.update(SQL1, toId, toConId, userConId);
			HaystackMessenger.getInstance().sendMatchMessage(toId, 
											hdbf.buildMeeting(c));
		}
		
	}
	
	protected HaystackMatcher() {}
	
	public static HaystackMatcher getInstance() {
		if(instance == null) {
			instance = new HaystackMatcher();
		}
		return instance;
	}
	
	public List<Connection> getConnectionMatches(Integer conId, String status) {
		String SQL = "select * from connections c where c.id in " +
			  		 "	 (select candConId from candidates cd " +
			  		 "	  where cd.userConId = ? " +
			  		 "	  and cd.status = ?)";
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {conId, status}, 
								   this.getRowMapper());
		return results;
	}
	
	public List<Connection> getMatchedConnections(Integer userId, String status) {
		String SQL = "select * from connections c where c.id in " +
	  		 			"(select distinct userConId from candidates cd " +
	  		 			 "where cd.userId = ? " +
	  		 			 "and cd.status = ?)";
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {userId, status}, 
						   		   this.getRowMapper());
		return results;
	}
	
	public Boolean areCandidates(Integer mId, Integer candId) {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		Connection connection = connectionJDBCTemplate.getByMeetingId(mId);
		Integer userConId = connection.getId();
		connection = connectionJDBCTemplate.getByMeetingId(candId);
		Integer candConId = connection.getId();
		String SQL = "select * from connections c where c.id in" +
						 "(select userConId from candidates cd " +
			  		 	  "where cd.userConId = ? " +
			  		 	  "and cd.candConId = ?)";
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {userConId, candConId}, 
		   		   				   this.getRowMapper());
		return !(results.isEmpty());
	}
	
	public void updateCandidateStatus(String status, Integer userMeetingId, Integer candMeetingId) {
		Integer userConId = this.getByMeetingId(userMeetingId).getId();
		Integer candConId = this.getByMeetingId(candMeetingId).getId();
		String SQL = "update candidates " +
					 "set status = ? " +
					 "where userConId = ? " +
					 "and candConId = ?";
		jdbcTemplateObject.update(SQL,status,userConId,candConId);
		if (status.equals("accepted")) {
			HaystackConnector.getInstance().updateSharedConnections(userConId, candConId);
			HaystackConnector.getInstance().checkForSharedConnection(userConId, candConId);
		}
	}
	
	public List<Meeting> getCandidates(Meeting target) {
		Integer tId = target.getId();
		Integer cId = this.getByMeetingId(tId).getId();
		Context context = target.getContexts().get(0);
		Float lat = context.getLocation().getLat();
		Float lon = context.getLocation().getLongd();
		Integer rad = context.getLocation().getRad();
		Date earliest = context.getEarliest();
		Date latest = context.getLatest();
		Integer userId = hdbf.getUserId(tId);
		Boolean isJourney = context.getLocationType().equals("journey");
		//find matching contexts based on location
		String SQL = 
				"insert into candidates (userId, userConId, candConId) " +
				"select ?, ?, c.id " +
				"from connections c " +
				"where c.conType = 'meeting' " +
				"and c.status = 'unresolved' " +
				"and c.userId <> ? " +
				"and exists " +
				   "(select * " +
				    "from contexts cx " +
					"where cx.earliest < ? " +
					"and cx.latest > ? " +
					"and cx.conId = c.id " +
					"and exists " +
					   "(select * " +
						"from locations l " +
						"where l.ctxId = cx.id " +
						"and ? + l.rad >= " +
							"(6371 * acos(cos(radians(?)) * cos(radians(l.lat)) * " +
							"cos(radians(l.longd) - radians(?)) + " +
							"sin(radians(?)) * sin(radians(l.lat)))) * 1000" +
						"))";
		jdbcTemplateObject.update(SQL,userId,cId,userId,latest,
								  earliest,rad,lat,lon,lat);
		if (isJourney) {
			String type = context.getJourney().getType();
			String company = context.getJourney().getCompany();
			//find matching contexts based on journey
			String SQL1 = 
					"insert into candidates (userId, userConId, candConId) " +
					"select ?, ?, c.id " +
					"from connections c " +
					"where c.conType = 'meeting' " +
					"and c.status = 'unresolved' " +
					"and c.userId <> ? " +
					"and exists " +
						"(select * " +
						"from contexts cx " +
						"where cx.earliest < ? " +
						"and cx.latest > ? " +
						"and cx.conId = c.id " +
						"and exists " +
							"(select * " +
							"from journeys j " +
							"where j.ctxId = cx.id " +
							"and j.type = ? " +
							"and j.company = ?)) " +
					"and c.id not in  " +
						"(select cd.candConId " +
						"from candidates cd " +
						"where userId = ? " +
						"and userConId = ?)";
			jdbcTemplateObject.update(SQL1,userId,cId,userId,latest,
									  earliest,type,company,userId,cId);
		}
		
		List<Connection> results = this.getConnectionMatches(cId, "under review");
		
		if (results.isEmpty()) {
			throw new EmptyResultDataAccessException(tId);
		}
		
		//else
		this.addCorrespondingCandidates(userId, cId);
		HaystackMessenger.getInstance().sendMatchMessage(userId, target);
		List<Meeting> candidates = hdbf.connectionsToMeetings(results);
		return candidates;
	}
	
}

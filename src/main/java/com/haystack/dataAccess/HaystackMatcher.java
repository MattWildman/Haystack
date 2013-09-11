package com.haystack.dataAccess;

import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.haystack.entities.Connection;
import com.haystack.entities.Context;
import com.haystack.entities.Meeting;

public class HaystackMatcher extends ConnectionJDBCTemplate {
	
	private static HaystackMatcher instance = null;
	
	private HaystackDBFacade hdbf = new HaystackDBFacade();
	
	protected HaystackMatcher() {}
	
	public static HaystackMatcher getInstance() {
		if(instance == null) {
			instance = new HaystackMatcher();
		}
		return instance;
	}
	
	public List<Connection> getConnectionMatches(Integer conId, String status) {
		
		String SQL = "select * from connections c " +
					 "where c.id in " +
			  		 "	 (select candConId from candidates cd " +
			  		 "	  where cd.userConId = ? " +
			  		 "	  and cd.status = ?" +
			  		 "	  and cd.userConId not in" +
			  		 "	  	(select cd1.candConId " +
			  		 "		 from candidates cd1 " +
			  		 "		 where cd1.userConId = cd.candConId " +
			  		 "	     and status = 'rejected'))";
		
		List<Connection> results = jdbcTemplateObject.query(SQL, 
								   new Object[] {conId, status}, 
								   this.getRowMapper());
		return results;
	}
	
	public List<Connection> getMatchedConnections(Integer userId, String status) {
		
		String SQL = "select * from connections c where c.id in " +
	  		 			"(select distinct cd.userConId " +
	  		 			 "from candidates cd " +
	  		 			 "where cd.userId = ? " +
	  		 			 "and cd.status = ? " +
	  		 			 "and exists " +
	  		 			 "	(select * " +
	  		 			 "	 from candidates cd1 " +
	  		 			 "	 where cd1.candConId = cd.userConId " +
	  		 			 "	 and status <> 'rejected'))";
		
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
		
		String SQL = "SELECT * FROM connections c WHERE c.id IN " +
					 "	 (SELECT userConId FROM candidates cd " +
			  		 "	  WHERE cd.userConId = ? " +
			  		 "	  AND cd.candConId = ? " +
			  		 "	  AND cd.status <> 'rejected')";
		
		SqlRowSet srs = jdbcTemplateObject.queryForRowSet(SQL, 
								   new Object[] {candConId, userConId});
		return srs.first();
	}
	
	public void updateCandidateStatus(String status, Integer userMeetingId, 
									  Integer candMeetingId) {
		
		Integer userConId = this.getByMeetingId(userMeetingId).getId();
		Integer candConId = this.getByMeetingId(candMeetingId).getId();
		
		String SQL = "UPDATE candidates " +
					 "SET status = ? " +
					 "WHERE userConId = ? " +
					 "AND candConId = ?";
		
		jdbcTemplateObject.update(SQL,status,userConId,candConId);

	}
	
	public List<Meeting> getCandidates(Meeting target) {
		Integer tId = target.getId();
		Integer cId = this.getByMeetingId(tId).getId();
		Context context = target.getContexts().get(0);
		Boolean isJourney = context.getLocationType().equals("journey");
		Float lat = isJourney ? context.getJourney().getStart().getLat():
								context.getLocation().getLat();
		Float lon = isJourney ? context.getJourney().getStart().getLongd():
								context.getLocation().getLongd();
		Integer rad = isJourney ? context.getJourney().getStart().getRad():
								  context.getLocation().getRad();
		Date earliest = context.getEarliest();
		Date latest = context.getLatest();
		Integer userId = hdbf.getUserId(tId);
		
		//find matching contexts based on location or start of journey
		String SQL = 
				"select * " +
				"from connections c " +
				"where c.conType = 'meeting' " +
				"and c.status <> 'resolved' " +
				"and c.userId <> ? " +
				"and c.id not in  " +
				"	(select cd.candConId " +
				" 	 from candidates cd " +
				" 	 where cd.userId = ? " +
				" 	 and cd.userConId = ?) " +
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
		
		List<Connection> results = jdbcTemplateObject.query(SQL, new Object[] 
		{userId,userId,cId,latest,earliest,rad,lat,lon,lat}, this.getRowMapper());
		
		if (isJourney) {
			//find matching contexts based on end of journey
			lat = context.getJourney().getEnd().getLat();
			lon = context.getJourney().getEnd().getLongd();
			rad = context.getJourney().getEnd().getRad();
			
			List<Connection> tempResults = jdbcTemplateObject.query(SQL, new Object[] 
			{userId,userId,cId,latest,earliest,rad,lat,lon,lat}, this.getRowMapper());
			
			results.addAll(tempResults);
		}
		
		if (results.isEmpty()) {
			throw new EmptyResultDataAccessException(tId);
		}
		
		//else
		//add matching pairs to candidates table
		for(Connection c : results) {
			String SQL1 = "insert into candidates (userId, userConId, candConId) " +
					  	  "values (?, ?, ?)";
			Integer otherUserId = c.getUserId();
			Integer otherConId = c.getId();
			jdbcTemplateObject.update(SQL1, userId, cId, otherConId);
			jdbcTemplateObject.update(SQL1, otherUserId, otherConId, cId);
			HaystackMessenger.getInstance().sendMatchMessage(otherUserId, 
											hdbf.buildMeeting(c));
		}

		HaystackMessenger.getInstance().sendMatchMessage(userId, target);
		List<Meeting> candidates = hdbf.connectionsToMeetings(results);
		return candidates;
	}

	public Integer getPendingCount(Integer userId) {
		
		String SQL = "SELECT COUNT(*) " +
				 	 "FROM candidates " +
				 	 "WHERE userId = ? " +
				 	 "AND status = 'pending'";
	
		Integer pendingCount = jdbcTemplateObject.queryForInt(SQL, userId);
		
		return pendingCount;
	}
	
}

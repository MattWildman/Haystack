package com.haystack.dataAccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.haystack.controllers.SecurityNavigation;
import com.haystack.entities.Connection;
import com.haystack.entities.Context;
import com.haystack.entities.Journey;
import com.haystack.entities.Location;
import com.haystack.entities.Meeting;
import com.haystack.entities.Participant;
import com.haystack.entities.User;

public class HaystackDBFacade {
	
	private ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
	private ContextJDBCTemplate contextJDBCTemplate = new ContextJDBCTemplate();
	private LocationJDBCTemplate locationJDBCTemplate = new LocationJDBCTemplate();
	private ParticipantJDBCTemplate participantJDCBTemplate = new ParticipantJDBCTemplate();
	private MeetingJDBCTemplate meetingJDBCTemplate = new MeetingJDBCTemplate();
	private UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
	
	public List<Meeting> connectionsToMeetings(List<Connection> connections) {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();	
		for (Connection c : connections) {
			meetings.add(this.buildMeeting(c));
		}
		return meetings;
	}
	
	public List<Meeting> getUserMeetings(Integer userId) {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		List<Connection> connections = connectionJDBCTemplate.getUserMeetings(userId);
		return connectionsToMeetings(connections);
	}
	
	public Meeting getMeeting(Integer meetingId) throws EmptyResultDataAccessException {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		Connection connection = connectionJDBCTemplate.getByMeetingId(meetingId);
		if (connection == null) {
			throw new EmptyResultDataAccessException(meetingId);
		}
		return buildMeeting(connection);
	}
	
	public Meeting buildMeeting(Integer connectionId) {
		return this.buildMeeting(connectionJDBCTemplate.getById(connectionId));
	}

	public Meeting buildMeeting(Connection c) {
		Integer conId = c.getId();
		String title = c.getTitle();
		String summary = c.getSummary();				
		Meeting meeting = meetingJDBCTemplate.getByOwnerId("conId", conId).get(0);
		Context context = contextJDBCTemplate.getByOwnerId("conId", conId).get(0);	
		if (context.getLocationType().equals("location")) {			
			Location location = locationJDBCTemplate.getByOwnerId("ctxId", context.getId()).get(0);		
			context.setLocation(location);		
		}	
		else {			
			JourneyJDBCTemplate journeyJDBCTemplate = new JourneyJDBCTemplate();
			Journey journey = journeyJDBCTemplate.getByOwnerId("ctxId", context.getId()).get(0);
			context.setJourney(journey);
		}
		Participant otherDesc = participantJDCBTemplate.getByOwnerId("meetingId", meeting.getId()).get(1);	
		meeting.setContexts(new ArrayList<Context>());
		meeting.addContext(context);
		meeting.setParticipants(new ArrayList<Participant>());
		meeting.addParticipant(otherDesc);
		meeting.setTitle(title);
		meeting.setSummary(summary);		
		return meeting;	
	}
	
	public Integer storeMeeting(Meeting meeting) {
		Connection connection = meeting;
		Context context = meeting.getContexts().get(0);
		Participant user = meeting.getUser();
		Participant other = meeting.getParticipants().get(0);
		
		Integer loggedInUserId = SecurityNavigation.getLoggedInUserId();	
		
		connection.setStatus("unresolved");		
		
		Integer conId = connectionJDBCTemplate.saveAndReturnKey(connection, loggedInUserId);
		Integer ctxId = contextJDBCTemplate.saveAndReturnKey(context, conId);
		Integer meetingId = meetingJDBCTemplate.saveAndReturnKey(meeting, conId);
		
		if (context.getLocationType().equals("location")) {
			Location location  = context.getLocation();
			locationJDBCTemplate.saveAndReturnKey(location, ctxId);
		}
		else {
			Journey journey = context.getJourney();
			JourneyJDBCTemplate journeyJDBCTemplate = new JourneyJDBCTemplate();
			Integer journeyId = journeyJDBCTemplate.saveAndReturnKey(journey, ctxId);
			Location start = journey.getStart();
			Location end = journey.getEnd();
			Integer startId = locationJDBCTemplate.saveAndReturnKey(start, ctxId);
			Integer endId = locationJDBCTemplate.saveAndReturnKey(end, ctxId);
			journeyJDBCTemplate.updateStartId(journeyId, startId);
			journeyJDBCTemplate.updateEndId(journeyId, endId);
			locationJDBCTemplate.updateJourneyId(startId, journeyId);
			locationJDBCTemplate.updateJourneyId(endId, journeyId);
		}
		
		Integer userId = participantJDCBTemplate.saveAndReturnKey(user, meetingId);
		participantJDCBTemplate.save(other, meetingId);
		meetingJDBCTemplate.updateUserId(meetingId, userId);
		return meetingId;
	}

	public Integer getUserId(Integer meetingId) {
		Connection connection = connectionJDBCTemplate.getByMeetingId(meetingId);
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User owner = userJDBCTemplate.getByConnectionId(connection.getId());
		return owner.getId();
	}

	public List<Meeting> getMatchedMeetings(Integer userId, String status) {
		List<Connection> connections = HaystackMatcher.getInstance().
									   getMatchedConnections(userId, status);
		return connectionsToMeetings(connections);
	}

	public List<Meeting> getMeetingMatches(Integer meetingId, String status) {
		Integer conId = connectionJDBCTemplate.getByMeetingId(meetingId).getId();
		List<Connection> connections = HaystackMatcher.getInstance().
				         			   getConnectionMatches(conId, status);
		return connectionsToMeetings(connections);
	}

	public Boolean checkForSharedConnections(Integer mId, Integer id) {
		Integer userConId = connectionJDBCTemplate.getByMeetingId(mId).getId();
		Integer candConId = connectionJDBCTemplate.getByMeetingId(id).getId();
		return HaystackConnector.getInstance()
								.checkForSharedConnection(userConId, candConId);
	}
	
	public Integer getConnectionId(Integer meetingId) {
		return connectionJDBCTemplate.getByMeetingId(meetingId).getId();
	}

	public List<User> getContacts(Integer userId) {
		return userJDBCTemplate.getContacts(userId);
	}
	
}

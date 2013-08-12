package com.haystack.dataAccess;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.haystack.controllers.SecurityNavigation;
import com.haystack.entities.Connection;
import com.haystack.entities.Context;
import com.haystack.entities.Journey;
import com.haystack.entities.Location;
import com.haystack.entities.Meeting;
import com.haystack.entities.Participant;
import com.haystack.entities.User;

@Repository
public class HaystackDBFacade {
	
	public List<Meeting> getUserMeetings(Integer userId) {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		List<Connection> connections = connectionJDBCTemplate.getUserMeetings(userId);
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();	
		for (Connection c : connections) {
			meetings.add(this.buildMeeting(c));
		}
		return meetings;
	}
	
	public Meeting getMeeting(Integer id) throws EmptyResultDataAccessException {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		Connection connection = connectionJDBCTemplate.getByMeetingId(id);
		if (connection == null) {
			throw new EmptyResultDataAccessException(id);
		}
		return buildMeeting(connection);
	}

	private Meeting buildMeeting(Connection c) {
		Integer conId = c.getId();
		String title = c.getTitle();
		String summary = c.getSummary();		
		ContextJDBCTemplate contextJDBCTemplate = new ContextJDBCTemplate();
		LocationJDBCTemplate locationJDBCTemplate = new LocationJDBCTemplate();
		ParticipantJDBCTemplate participantJDCBTemplate = new ParticipantJDBCTemplate();
		MeetingJDBCTemplate meetingJDBCTemplate = new MeetingJDBCTemplate();		
		Meeting meeting = meetingJDBCTemplate.getByOwnerId("conId", conId).get(0);
		Context context = contextJDBCTemplate.getByOwnerId("conId", conId).get(0);	
		DateTime edt = new DateTime(context.getEarliest());
		DateTime ldt = new DateTime(context.getLatest());
		if (edt.getDayOfYear() == ldt.getDayOfYear() && 
			edt.getYear() == ldt.getYear()) {
			context.setEarliestString(edt.toString("'On' EEE d MMM yyyy 'between' HH:mm"));
			context.setLatestString(ldt.toString("HH:mm"));
		}
		else {
			context.setEarliestString(edt.toString("'Between' EEE d MMM yyyy 'at' HH:mm"));
			context.setLatestString(ldt.toString("EEE d MMM yyyy 'at' HH:mm"));
		}
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
	
	public void storeMeeting(Meeting meeting) {
		Connection connection = meeting;
		Context context = meeting.getContexts().get(0);		
		//set the format to match the pattern received from client form
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		DateTime edt = format.parseDateTime(context.getEarliestString());
		DateTime ldt = format.parseDateTime(context.getLatestString());
		context.setEarliest(edt.toDate());
		context.setLatest(ldt.toDate());
		Participant user = meeting.getUser();
		Participant other = meeting.getParticipants().get(0);		
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		ContextJDBCTemplate contextJDBCTemplate = new ContextJDBCTemplate();
		MeetingJDBCTemplate meetingJDBCTemplate = new MeetingJDBCTemplate();
		LocationJDBCTemplate locationJDBCTemplate = new LocationJDBCTemplate();
		ParticipantJDBCTemplate participantJDCBTemplate = new ParticipantJDBCTemplate();
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
	}

	public Integer getUserId(Integer meetingId) {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		Connection connection = connectionJDBCTemplate.getByMeetingId(meetingId);
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User owner = userJDBCTemplate.getByConnectionId(connection.getId());
		return owner.getId();
	}

}

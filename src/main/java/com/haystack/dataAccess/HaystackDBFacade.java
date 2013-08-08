package com.haystack.dataAccess;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.haystack.entities.Connection;
import com.haystack.entities.Context;
import com.haystack.entities.Journey;
import com.haystack.entities.Location;
import com.haystack.entities.Meeting;
import com.haystack.entities.Participant;

@Repository
public class HaystackDBFacade {
	
	public List<Meeting> getUserMeetings(Integer userId) {
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		List<Connection> connections = connectionJDBCTemplate.getUserMeetings(userId);
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();	
		for (Connection c : connections) {
			meetings.add(this.buildMeeting(c));
		}
		return null;
	}

	private Meeting buildMeeting(Connection c) {
		Integer conId = c.getId();
		String title = c.getTitle();
		String summary = c.getSummary();		
		ContextJDBCTemplate contextJDBCTemplate = new ContextJDBCTemplate();
		LocationJDBCTemplate locationJDBCTemplate = new LocationJDBCTemplate();
		ParticipantJDBCTemplate participantJDCBTemplate = new ParticipantJDBCTemplate();
		MeetingJDBCTemplate meetingJDBCTemplate = new MeetingJDBCTemplate();		
		Meeting meeting = meetingJDBCTemplate.getByOwnerId("conID", conId).get(0);
		Context context = contextJDBCTemplate.getByOwnerId("conId", conId).get(0);	
		if (context.getLocationType().equals("location")) {			
			Location location = locationJDBCTemplate.getByOwnerId("ctxId", context.getId()).get(0);		
			context.setLocation(location);		
		}	
		else {			
			JourneyJDBCTemplate journeyJDBCTemplate = new JourneyJDBCTemplate();
			Journey journey = context.getJourney();
			Integer startId = (Integer) journeyJDBCTemplate.getValueById(journey.getId(), "startId");
			Integer endId = (Integer) journeyJDBCTemplate.getValueById(journey.getId(), "endId");
			Location start = locationJDBCTemplate.getById(startId);
			Location end = locationJDBCTemplate.getById(endId);			
			journey.setStart(start);
			journey.setEnd(end);
			context.setJourney(journey);
		}
		Integer userDescId = (Integer) meetingJDBCTemplate.getValueById(meeting.getId(), "userDesc");
		Participant userDesc = participantJDCBTemplate.getById(userDescId);
		Participant otherDesc = participantJDCBTemplate.getByOwnerId("meetingId", meeting.getId()).get(0);	
		meeting.setContexts(new ArrayList<Context>());
		meeting.addContext(context);
		meeting.setParticipants(new ArrayList<Participant>());
		meeting.addParticipant(otherDesc);
		meeting.setUser(userDesc);
		meeting.setTitle(title);
		meeting.setSummary(summary);		
		return meeting;	
	}
	
	public void storeMeeting(Meeting meeting) {
		Connection connection = meeting;
		Context context = meeting.getContexts().get(0);		
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
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User loggedInUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUsername = loggedInUser.getUsername();
		Integer loggedInUserId = userJDBCTemplate.getByUsername(loggedInUsername).getId();		
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

}

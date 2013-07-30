package com.haystack.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haystack.dataAccess.ConnectionJDBCTemplate;
import com.haystack.dataAccess.ContextJDBCTemplate;
import com.haystack.dataAccess.JourneyJDBCTemplate;
import com.haystack.dataAccess.LocationJDBCTemplate;
import com.haystack.dataAccess.MeetingJDBCTemplate;
import com.haystack.dataAccess.ParticipantJDBCTemplate;
import com.haystack.entities.Connection;
import com.haystack.entities.Context;
import com.haystack.entities.Journey;
import com.haystack.entities.Location;
import com.haystack.entities.Meeting;
import com.haystack.entities.Participant;
import com.haystack.validation.MeetingSearchValidation;

@Controller
@RequestMapping("/FindSomeone")
public class MeetingController {
	
	@Autowired
    private MeetingSearchValidation meetingSearchValidation;
	
	public void setMeetingSearchValidation(MeetingSearchValidation meetingSearchValidation) {
		this.meetingSearchValidation = meetingSearchValidation;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String findSomeonePage(Map<String, Meeting> model) {
		Meeting meeting = new Meeting();
		model.put("meeting", meeting);
		return "meeting-form";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String processMeetingSearch(Meeting meeting, BindingResult result) {
		meetingSearchValidation.validate(meeting, result);
		if (result.hasErrors()) {
			return "meeting-form";
		}
		this.storeMeetingDetails(meeting);
		return "meeting-posted";
	}
	
	@Transactional
	private void storeMeetingDetails(Meeting meeting) {
		
		Connection connection = meeting;
		Context context = meeting.getContexts().get(0);
		Participant user = meeting.getUser();
		Participant other = meeting.getParticipants().get(0);
		
		ConnectionJDBCTemplate connectionJDBCTemplate = new ConnectionJDBCTemplate();
		ContextJDBCTemplate contextJDBCTemplate = new ContextJDBCTemplate();
		MeetingJDBCTemplate meetingJDBCTemplate = new MeetingJDBCTemplate();
		LocationJDBCTemplate locationJDBCTemplate = new LocationJDBCTemplate();
		ParticipantJDBCTemplate participantJDCBTemplate = new ParticipantJDBCTemplate();

		connection.setStatus("unresolved");
		
		Integer conId = connectionJDBCTemplate.saveAndReturnKey(connection, 1); //TODO get actual user id from session
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

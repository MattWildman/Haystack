package com.haystack.controllers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.dataAccess.HaystackMatcher;
import com.haystack.entities.Context;
import com.haystack.entities.Meeting;
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
	public ModelAndView findSomeonePage() {
		ModelAndView modelAndView = GeneralNavigation.renderPage("Find somone", "meeting-form");
		Meeting meeting = new Meeting();
		modelAndView.addObject("meeting", meeting);
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processMeetingSearch(Meeting meeting, BindingResult result) {
		meetingSearchValidation.validate(meeting, result);
		if (result.hasErrors()) {
			return GeneralNavigation.renderPage("Find somone", "meeting-form");
		}
		
		Context context = meeting.getContexts().get(0);		
		//set the format to match the pattern received from client form
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		DateTime edt = format.parseDateTime(context.getEarliestString());
		DateTime ldt = format.parseDateTime(context.getLatestString());
		context.setEarliest(edt.toDate());
		context.setLatest(ldt.toDate());
		
		HaystackDBFacade haystackDBFacade = new HaystackDBFacade();
		List<Meeting> candidates = new ArrayList<Meeting>();
		
		Integer meetingId = haystackDBFacade.storeMeeting(meeting);
		meeting.setId(meetingId);
		
		try {
			candidates = HaystackMatcher.getInstance().getCandidates(meeting);
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("No matches found yet", "meeting-posted");
		}
		
		ModelAndView modelAndView = GeneralNavigation.renderPage("Success!", "meeting-posted");
		modelAndView.addObject("candidates", candidates);
		return modelAndView;
	}

}

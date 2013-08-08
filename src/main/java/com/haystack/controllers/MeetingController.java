package com.haystack.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haystack.dataAccess.HaystackDBFacade;
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
		HaystackDBFacade haystackDBFacade = new HaystackDBFacade();
		haystackDBFacade.storeMeeting(meeting);
		return "meeting-posted";
	}

}

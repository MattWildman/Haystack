package com.haystack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		HaystackDBFacade haystackDBFacade = new HaystackDBFacade();
		haystackDBFacade.storeMeeting(meeting);
		return GeneralNavigation.renderPage("Meeting posted", "meeting-posted");
	}

}

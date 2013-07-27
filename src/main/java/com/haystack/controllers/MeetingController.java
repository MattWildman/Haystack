package com.haystack.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haystack.entities.MeetingSearch;

@Controller
@RequestMapping("/findSomeone")
public class MeetingController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String findSomeonePage(Map<String, MeetingSearch> model) {
		MeetingSearch meetingSearch = new MeetingSearch();
		model.put("meetingSearch", meetingSearch);
		return "meeting-form";
	}

}

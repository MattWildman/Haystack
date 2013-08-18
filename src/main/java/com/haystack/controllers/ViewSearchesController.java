package com.haystack.controllers;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.entities.Context;
import com.haystack.entities.Meeting;

@Controller
public class ViewSearchesController {
	
	private HaystackDBFacade haystackDBFacade = new HaystackDBFacade();
	
	@RequestMapping(value="/Searches", method=RequestMethod.GET)
	public ModelAndView showSearches() {	
		ModelAndView modelAndView = GeneralNavigation.renderPage("Your searches", "searches");
		Integer loggedInUserId = SecurityNavigation.getLoggedInUserId();
		List<Meeting> meetings = haystackDBFacade.getUserMeetings(loggedInUserId);
		modelAndView.addObject("meetings", meetings);
		return modelAndView;
	}
	
	@RequestMapping(value="/Searches/{id}", method=RequestMethod.GET)
	public ModelAndView showSearch(@PathVariable Integer id) {
		Meeting meeting = null;
		Integer ownerId = 0;
		String meetingTitle = "";
		try {
			meeting = haystackDBFacade.getMeeting(id);
			ownerId = haystackDBFacade.getUserId(id);
			meetingTitle = meeting.getTitle();
			Context context = meeting.getContexts().get(0);
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
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Search not found", "404");
		}
		ModelAndView modelAndView = GeneralNavigation.renderPage(meetingTitle, "post");
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		if (ownerId != loggedInId) {
			return GeneralNavigation.renderPage("Not authorised", "not-authorised");
		}
		modelAndView.addObject("meeting", meeting);
		return modelAndView;
	}

}

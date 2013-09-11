package com.haystack.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackConnector;
import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.dataAccess.HaystackMatcher;
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
	public ModelAndView showSearch(@PathVariable Integer id,
								   @RequestParam(value="action", required=false) String action,
								   HttpServletRequest request) {
		
		Meeting meeting = null;
		Integer ownerId = 0;
		Integer conId = 0;
		String meetingTitle = "";
		Boolean hidden = false;
		
		try {
			
			meeting = haystackDBFacade.getMeeting(id);
			ownerId = haystackDBFacade.getUserId(id);
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised", "not-authorised");
			}
			
			conId = haystackDBFacade.getConnectionId(id);
			hidden = HaystackConnector.getInstance().isResolved(conId);
			meetingTitle = meeting.getTitle();
			Context context = meeting.getContexts().get(0);
			context.setDateTimeStrings();
			
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Search not found", "404");
		}
		
		ModelAndView modelAndView = GeneralNavigation.renderPage(meetingTitle, "post");
		
		if (request.getParameterMap().containsKey("action")) {
			if (action.equals("hide")) {
				hidden = true;
				HaystackConnector.getInstance().updateStatus(conId, "resolved");
			}
			if (action.equals("activate")) {
				hidden = false;
				HaystackConnector.getInstance().updateStatus(conId, "unresolved");
				try {
					HaystackMatcher.getInstance().getCandidates(meeting);
				} catch (EmptyResultDataAccessException e) {}
			}
		}
		modelAndView.addObject("hidden", hidden);
		modelAndView.addObject("meeting", meeting);
		return modelAndView;
	}

}

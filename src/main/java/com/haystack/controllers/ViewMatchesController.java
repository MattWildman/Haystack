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

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.dataAccess.HaystackMatcher;
import com.haystack.entities.Context;
import com.haystack.entities.Meeting;

@Controller
public class ViewMatchesController {
	
private HaystackDBFacade haystackDBFacade = new HaystackDBFacade();
	
	@RequestMapping(value="/Matches", method=RequestMethod.GET)
	public ModelAndView showMatchedMeetings() {	
		ModelAndView modelAndView = GeneralNavigation.renderPage("Matched searches", "matches");
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		List<Meeting> meetings = haystackDBFacade.getMatchedMeetings(loggedInId);
		modelAndView.addObject("meetings", meetings);
		return modelAndView;
	}
	
	@RequestMapping(value="/Matches/{id}", method=RequestMethod.GET)
	public ModelAndView showMatches(@PathVariable Integer id) {
		Meeting meeting = new Meeting();
		try {
			meeting = haystackDBFacade.getMeeting(id);
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			Integer ownerId = haystackDBFacade.getUserId(id);
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised", "not-authorised");
			}
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Search not found", "404");
		}
		String title = meeting.getTitle();
		List<Meeting> candidates = haystackDBFacade.getMeetingMatches(id);
		ModelAndView modelAndView = GeneralNavigation.renderPage("Pending matches for '" + title + "'", "candidates");
		modelAndView.addObject("meeting", meeting);
		modelAndView.addObject("candidates", candidates);
		return modelAndView;
	}
	
	@RequestMapping(value="/Matches/{mId}/{id}", method=RequestMethod.GET)
	public ModelAndView showMatch(@PathVariable Integer mId, 
								  @PathVariable Integer id,
								  @RequestParam(value="action", required=false) String action,
								  HttpServletRequest request) {
		Meeting candidate = null;
		Integer ownerId = 0;
		String candidateTitle = "";
		try {
			ownerId = haystackDBFacade.getUserId(mId);
			
			//security test
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised", "not-authorised");
			}
			
			candidate = haystackDBFacade.getMeeting(id);
			
			//security test
			if (!HaystackMatcher.getInstance().areCandidates(mId, id)) {
				return GeneralNavigation.renderPage("Match not found", "404");
			}
			
			candidateTitle = candidate.getTitle();
			
			Context context = candidate.getContexts().get(0);
			context.setDateTimeStrings();

		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Match not found", "404");
		}
		
		if (request.getParameterMap().containsKey("action")) {
			if (action.equals("accept")) {
				HaystackMatcher.getInstance().updateCandidateStatus("accepted", mId, id);
				ModelAndView acceptedView = GeneralNavigation.renderPage("You have accepted '" + candidateTitle + "'", 
											"match-accepted");
				return acceptedView;
			}
			else if (action.equals("reject")) {
				HaystackMatcher.getInstance().updateCandidateStatus("rejected", mId, id);
				ModelAndView rejectedView = GeneralNavigation.renderPage("You have rejected '" + candidateTitle + "'", 
											"match-rejected");
				String originalURL = "Matches/" + mId.toString();
				rejectedView.addObject("originalURL", originalURL);
				return rejectedView;
			}
		}
		
		ModelAndView modelAndView = GeneralNavigation.renderPage("Possible match: '" + candidateTitle + "'", "candidate");
		modelAndView.addObject("candidate", candidate);
		modelAndView.addObject("originalURL", request.getRequestURL());
		return modelAndView;
	}

}

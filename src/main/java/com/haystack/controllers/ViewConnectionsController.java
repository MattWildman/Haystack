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
public class ViewConnectionsController {
	
	private HaystackDBFacade hdbf = new HaystackDBFacade();
	
	@RequestMapping(value="/Connections", method=RequestMethod.GET)
	public ModelAndView showUserConnections() {
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		List<Meeting> sharedMeetings = hdbf.connectionsToMeetings(HaystackConnector.getInstance()
									   							  .getSharedConnections(loggedInId));
		
		ModelAndView modelAndView = GeneralNavigation.renderPage("Your shared connections", 
																 "shared-connections");
		modelAndView.addObject("connections", sharedMeetings);
		return modelAndView;
	}
	
	@RequestMapping(value="/Connections/{id}", method=RequestMethod.GET)
	public ModelAndView showConnections(@PathVariable Integer id) {
		
		Meeting meeting = null;
		Integer ownerId = 0;
		String meetingTitle = "";
		
		try {
			meeting = hdbf.getMeeting(id);
			ownerId = hdbf.getUserId(id);
			
			// security test
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised", "not-authorised");
			}
			
			meetingTitle = meeting.getTitle();
			
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Connection not found", "404");
		}
		
		List<Meeting> sharedMeetings = hdbf.connectionsToMeetings(
									   HaystackConnector.getInstance()
									   .getCorrespondingSharedConnections(id));
		
		ModelAndView modelAndView = GeneralNavigation.renderPage("Connections with " + meetingTitle, 
																 "connections");
		
		modelAndView.addObject("target", meeting);
		modelAndView.addObject("connections", sharedMeetings);
		return modelAndView;
		
	}
	
	@RequestMapping(value="/Connections/{cId}/{id}", method=RequestMethod.GET)
	public ModelAndView showConnection(@PathVariable Integer cId,
									   @PathVariable Integer id,
									   @RequestParam(value = "action", required = false) String action,
									   HttpServletRequest request) {
		Meeting connection = null;
		Integer ownerId = 0;
		String connectionTitle = "";
		try {
			ownerId = hdbf.getUserId(cId);

			// security test
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised",
						"not-authorised");
			}

			// security test
			if (!HaystackConnector.getInstance().areConnected(cId, id)) {
				return GeneralNavigation.renderPage("Connection not found", "404");
			}
			connection = hdbf.getMeeting(id);
			connectionTitle = connection.getTitle();
			Context context = connection.getContexts().get(0);
			context.setDateTimeStrings();

		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Match not found", "404");
		}

		if (request.getParameterMap().containsKey("action")) {
			if (action.equals("reject")) {

				HaystackMatcher.getInstance().updateCandidateStatus("rejected", cId, id);
				ModelAndView rejectedView = GeneralNavigation.renderPage(
											"You have rejected '" + connectionTitle + "'",
											"match-rejected");
				return rejectedView;
			}
		}

		ModelAndView modelAndView = GeneralNavigation.renderPage(connectionTitle, "connection");
		modelAndView.addObject("connection", connection);
		modelAndView.addObject("originalURL", request.getRequestURL());
		return modelAndView;
	}

}

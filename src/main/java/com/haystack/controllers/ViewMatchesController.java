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

	private HaystackDBFacade hdbf = new HaystackDBFacade();

	private Boolean goodStatus(String status) {
		return status.equalsIgnoreCase("pending")
				|| status.equalsIgnoreCase("accepted")
				|| status.equalsIgnoreCase("rejected");
	}

	@RequestMapping(value = "/Matches/{URLstatus}", method = RequestMethod.GET)
	public ModelAndView showMeetings(@PathVariable String URLstatus) {
		if (!this.goodStatus(URLstatus)) {
			return GeneralNavigation.renderPage("Page not found", "404");
		}
		String status = URLstatus.toLowerCase();
		ModelAndView modelAndView = GeneralNavigation.renderPage(
				"Searches with " + status + " matches", "matches");
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		List<Meeting> meetings = hdbf.getMatchedMeetings(loggedInId, status);
		modelAndView.addObject("status", status);
		modelAndView.addObject("meetings", meetings);
		return modelAndView;
	}

	@RequestMapping(value = "/Matches/{URLstatus}/{id}", method = RequestMethod.GET)
	public ModelAndView showMatches(@PathVariable String URLstatus,
									@PathVariable Integer id) {
		if (!this.goodStatus(URLstatus)) {
			return GeneralNavigation.renderPage("Page not found", "404");
		}
		String status = URLstatus.toLowerCase();
		Meeting meeting = new Meeting();
		try {
			meeting = hdbf.getMeeting(id);
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			Integer ownerId = hdbf.getUserId(id);
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised",
						"not-authorised");
			}
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Search not found", "404");
		}
		String title = meeting.getTitle();
		List<Meeting> candidates = hdbf.getMeetingMatches(id, status);
		ModelAndView modelAndView = GeneralNavigation.renderPage(URLstatus
				+ " matches for '" + title + "'", "candidates");
		modelAndView.addObject("status", status);
		modelAndView.addObject("meeting", meeting);
		modelAndView.addObject("candidates", candidates);
		return modelAndView;
	}

	@RequestMapping(value = "/Matches/{URLstatus}/{mId}/{id}", method = RequestMethod.GET)
	public ModelAndView showMatch(@PathVariable String URLstatus,
								  @PathVariable Integer mId, @PathVariable Integer id,
								  @RequestParam(value="action", required=false) String action,
								  HttpServletRequest request) {
		if (!this.goodStatus(URLstatus)) {
			return GeneralNavigation.renderPage("Page not found", "404");
		}
		String status = URLstatus.toLowerCase();
		Meeting candidate = null;
		Integer ownerId = 0;
		String candidateTitle = "";
		try {
			ownerId = hdbf.getUserId(mId);

			// security test
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			if (ownerId != loggedInId) {
				return GeneralNavigation.renderPage("Not authorised",
						"not-authorised");
			}
			candidate = hdbf.getMeeting(id);

			// security test
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
			if (action.equals("accept") && !status.equalsIgnoreCase("accepted")) {
				HaystackMatcher.getInstance().updateCandidateStatus("accepted", mId, id);

				ModelAndView acceptedView = GeneralNavigation.renderPage(
											"You have accepted '" + candidateTitle + "'",
											"match-accepted");

				Boolean mutualAcceptance = hdbf.checkForSharedConnections(mId, id);
				acceptedView.addObject("success", mutualAcceptance);
				return acceptedView;
			}

			else if (action.equals("reject") && !status.equalsIgnoreCase("rejected")) {

				HaystackMatcher.getInstance().updateCandidateStatus("rejected",
						mId, id);
				ModelAndView rejectedView = GeneralNavigation.renderPage(
											"You have rejected '" + candidateTitle + "'",
											"match-rejected");
				String originalURL = "/Matches/" + URLstatus + "/"
						+ mId.toString();
				rejectedView.addObject("originalURL", originalURL);
				return rejectedView;

			}
		}

		ModelAndView modelAndView = GeneralNavigation.renderPage(URLstatus +
									" match: '" + candidateTitle + "'", "candidate");
		modelAndView.addObject("status", status);
		modelAndView.addObject("candidate", candidate);
		modelAndView.addObject("originalURL", request.getRequestURL());
		return modelAndView;
	}

}

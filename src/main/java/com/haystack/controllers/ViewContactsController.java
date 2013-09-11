package com.haystack.controllers;

import java.util.List;
import java.util.Map;

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
import com.haystack.dataAccess.HaystackMessenger;
import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Meeting;
import com.haystack.entities.User;

@Controller
public class ViewContactsController {
	
	private HaystackDBFacade hdbf = new HaystackDBFacade();
	private UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();

	@RequestMapping(value="/Contacts", method=RequestMethod.GET)
	public ModelAndView showContacts() {
		
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		List<User> contacts = hdbf.getContacts(loggedInId);
		List<User> blockedContacts = hdbf.getBlockedContacts(loggedInId);
		
		ModelAndView modelAndView = GeneralNavigation.renderPage("Your contacts", 
																 "contacts");
		modelAndView.addObject("blockedContacts", blockedContacts);
		modelAndView.addObject("contacts", contacts);
		return modelAndView;
		
	}
	
	@RequestMapping(value="/Contacts/Blocked", method=RequestMethod.GET)
	public ModelAndView showBlockedContacts() {
		
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		ModelAndView modelAndView = GeneralNavigation.renderPage("Blocked contacts", 
				 												 "blocked-contacts");
		List<User> blockedContacts = hdbf.getBlockedContacts(loggedInId);
		modelAndView.addObject("blockedContacts", blockedContacts);
		return modelAndView;
	}
	
	@RequestMapping(value="/Contacts/{id}", method=RequestMethod.GET)
	public ModelAndView showContact(@PathVariable Integer id,
									@RequestParam(value="action", required=false) String action,
									HttpServletRequest request) {
		User contact = null;
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		
		try {
			
			contact = userJDBCTemplate.getById(id);
			
			if (request.getParameterMap().containsKey("action")) {
				if (action.equals("block")) {
					HaystackMessenger.getInstance().blockContact(loggedInId, id);
					ModelAndView blockedView = GeneralNavigation.renderPage(
											   contact.getUsername() + " blocked", 
							 				   "blocked-success");
					blockedView.addObject("contact", contact);
					return blockedView;
				}
				if (action.equals("unblock")) {
					HaystackMessenger.getInstance().unBlockContact(loggedInId, id);
					ModelAndView unblockedView = GeneralNavigation.renderPage(
												 contact.getUsername() + " unblocked", 
												 "unblocked-success");
					unblockedView.addObject("contact", contact);
					return unblockedView;
				}
			}
			
			if(!HaystackMessenger.getInstance().hasPermission(loggedInId, id)) {
				return GeneralNavigation.renderPage("Not authorised",
													"not-authorised");
			}
			
		} catch (EmptyResultDataAccessException e) {
			return GeneralNavigation.renderPage("Contact not found", "404");
		}
		
		Map<Meeting, List<Meeting>> sharedMeetings = HaystackConnector.getInstance()
													 .getHistory(loggedInId, id);
		
		ModelAndView modelAndView = GeneralNavigation.renderPage(contact.getUsername(), 
																 "contact");
		modelAndView.addObject("contact", contact);
		modelAndView.addObject("connections", sharedMeetings);
		return modelAndView;
		
	}
	
}

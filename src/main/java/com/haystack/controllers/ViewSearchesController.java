package com.haystack.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Meeting;

@Controller
@RequestMapping(value="/Searches", method=RequestMethod.GET)
public class ViewSearchesController {
	
	public ModelAndView showSearches() {	
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		ModelAndView modelAndView = new ModelAndView("searches");
		User loggedInUser = (User)SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();
		String loggedInUsername = loggedInUser.getUsername();
		Integer loggedInUserId = userJDBCTemplate.getByUsername(loggedInUsername).getId();
		HaystackDBFacade haystackDBFacade = new HaystackDBFacade();
		List<Meeting> meetings = haystackDBFacade.getUserMeetings(loggedInUserId);
		modelAndView.addObject("meetings", meetings);
		return modelAndView;
	}

}

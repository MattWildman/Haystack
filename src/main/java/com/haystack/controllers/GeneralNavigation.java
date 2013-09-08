package com.haystack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackMatcher;
import com.haystack.dataAccess.HaystackMessenger;

@Controller
public class GeneralNavigation {
	
	public static ModelAndView renderPage(String title, String bodyTemplate) {
		ModelAndView modelAndView = new ModelAndView("Haystack-template");
		Integer unreadCount = 0;
		Integer pendingCount = 0;
		try {
			Integer loggedInId = SecurityNavigation.getLoggedInUserId();
			unreadCount = HaystackMessenger.getInstance()
						  .getUnreadCount(loggedInId);
			pendingCount = HaystackMatcher.getInstance()
						   .getPendingCount(loggedInId);
		}
		catch (Exception e) {}
		modelAndView.addObject("unreadCount", unreadCount);
		modelAndView.addObject("pendingCount", pendingCount);
		modelAndView.addObject("contentTitle", title);
		modelAndView.addObject("contentBody", bodyTemplate + ".jsp");
		return modelAndView;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView homePage() {
		return GeneralNavigation.renderPage("Home", "index");
	}

}

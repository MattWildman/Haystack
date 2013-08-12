package com.haystack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.User;

@Controller
public class AccountController {
	
	@RequestMapping(value="/Account", method=RequestMethod.GET)
	public ModelAndView accountPage() {
		ModelAndView modelAndView = GeneralNavigation.renderPage("Account", "account");
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User user = userJDBCTemplate.getById(loggedInId);
		modelAndView.addObject("user", user);
		return modelAndView;
	}

}

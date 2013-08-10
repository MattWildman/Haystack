package com.haystack.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.UserJDBCTemplate;

@Controller
public class SecurityNavigation {
	
	public static Integer getLoggedInUserId() {
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User loggedInUser = (User)SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String loggedInUsername = loggedInUser.getUsername();
		return userJDBCTemplate.getByUsername(loggedInUsername).getId();
	}
	
	@RequestMapping(value="/Login", method=RequestMethod.GET)
	public ModelAndView loginForm() {
		return GeneralNavigation.renderPage("Log in", "login");
	}
	
	@RequestMapping(value="/Login-error", method=RequestMethod.GET)
	public ModelAndView invalidLogin() {
		ModelAndView modelAndView = GeneralNavigation.renderPage("Log in error", "login");
		modelAndView.addObject("error", true);
		return modelAndView;
	}
	
	@RequestMapping(value="/Login-success", method=RequestMethod.GET)
	public ModelAndView successLogin() {
		return GeneralNavigation.renderPage("Home", "index");
	}
	
	@RequestMapping(value="/Logout", method=RequestMethod.GET)
	public ModelAndView logout() {
		return GeneralNavigation.renderPage("Logged out", "logout");
	}

}
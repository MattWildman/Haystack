package com.haystack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralNavigation {
	
	public static ModelAndView renderPage(String title, String bodyTemplate) {
		ModelAndView modelAndView = new ModelAndView("Haystack-template");
		modelAndView.addObject("contentTitle", title);
		modelAndView.addObject("contentBody", bodyTemplate + ".jsp");
		return modelAndView;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView homePage() {
		return GeneralNavigation.renderPage("Home", "index");
	}
	
	@RequestMapping(value="/Matches", method=RequestMethod.GET)
	public ModelAndView showMatchesIndexPage() {
		return GeneralNavigation.renderPage("Your matches", "all-matches");
	}
	
	@RequestMapping(value="/findSomething", method=RequestMethod.GET)
	public ModelAndView findItemPage() {
		return new ModelAndView("item-form");
	}

}

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
		System.out.println("Rendering " + title);
		return modelAndView;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView homePage() {
		return GeneralNavigation.renderPage("Home", "index");
	}
	
	@RequestMapping(value="/findSomething", method=RequestMethod.GET)
	public ModelAndView findItemPage() {
		return new ModelAndView("item-form");
	}
	
	@RequestMapping(value="/Account", method=RequestMethod.GET)
	public ModelAndView accountPage() {
		return GeneralNavigation.renderPage("Account", "account");
	}
	
	@RequestMapping(value="/Inbox", method=RequestMethod.GET)
	public ModelAndView inboxPage() {
		return GeneralNavigation.renderPage("Inbox", "inbox");
	}
	
	@RequestMapping(value="/Inbox/{id}", method=RequestMethod.GET)
	public ModelAndView messagePage() {
		return GeneralNavigation.renderPage("Message title", "message");
	}

}

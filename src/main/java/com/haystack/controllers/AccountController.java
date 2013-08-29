package com.haystack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Registration;
import com.haystack.entities.User;
import com.haystack.validation.AccountValidation;

@Controller
@RequestMapping("/Account")
public class AccountController {
	
	@Autowired
	private AccountValidation accountValidation;
	
	public void setAccountValidation(AccountValidation accountValidation) {
		this.accountValidation = accountValidation;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView accountPage() {
		ModelAndView modelAndView = GeneralNavigation.renderPage("Account", "account");
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User user = userJDBCTemplate.getById(loggedInId);
		modelAndView.addObject("user", user);
		modelAndView.addObject("registration", new Registration());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView updateAccount(Registration registration, 
									  BindingResult result) {
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User user = userJDBCTemplate.getById(loggedInId);
		accountValidation.setUser(user);
		accountValidation.validate(registration, result);
		if (result.hasErrors()) {
			return GeneralNavigation.renderPage("Account", "account");
		}
		if (!registration.getUsername().equals(user.getUsername())) {
			userJDBCTemplate.updateUsername(loggedInId, registration.getUsername());
		}
		if (!registration.getNewPassword().isEmpty()) {
			userJDBCTemplate.updatePassword(loggedInId, registration.getNewPassword());
		}
		if (!registration.getEmail().equals(user.getEmail())) {
			userJDBCTemplate.updateEmail(loggedInId, registration.getEmail());
		}
		ModelAndView modelAndView = GeneralNavigation.renderPage("Account", "account");
		return modelAndView;
	}

}

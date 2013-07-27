package com.haystack.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.haystack.entities.Meeting;

@Component("meetingSearchValidator")
public class MeetingSearchValidation {
	
	public boolean supports(Class<?> clazz) {
		return Meeting.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		
		Meeting meeting = (Meeting) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"NotEmpty.registration.username",
				"User name is required.");
		
		//TODO Finish this!

	}

}

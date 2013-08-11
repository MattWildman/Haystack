package com.haystack.validation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.haystack.entities.Context;
import com.haystack.entities.Meeting;

@Component("meetingSearchValidator")
public class MeetingSearchValidation implements Validator {
	
	public boolean supports(Class<?> clazz) {
		return Meeting.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		
		Meeting meeting = (Meeting) target;
		Context context = meeting.getContexts().get(0);
		Boolean usingLocation = meeting.getContexts().get(0)
								.getLocationType().equals("location");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
					  "NotEmpty.meetingSearch.title", "Title is required");
		
		if (usingLocation) {			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.title",
					  "NotEmpty.meetingSearch.locationTitle",
					  "Name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.area",
					  "NotEmpty.meetingSearch.locationArea",
					  "Area is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.rad",
					  "NotEmpty.meetingSearch.locationRad",
					  "Radius is required.");
		}
		else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].journey.type",
					  "NotEmpty.meetingSearch.journeyType",
					  "Transport type is required.");		
		}
		
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		DateTime edt = format.parseDateTime(context.getEarliestString());
		DateTime ldt = format.parseDateTime(context.getLatestString());
		
		if (ldt.isBefore(edt)) {
			errors.rejectValue("contexts[0].latestString",
					"illogicalDate.meetingSearch.latest",
					"The later date cannot be earlier than the earlier date!");
		}
		if (ldt.isAfterNow() || edt.isAfterNow()) {
			errors.rejectValue("contexts[0].latestString",
					"illogicalDate.meetingSearch.latest",
					"Date cannot be in the future!");
		}
		
		

	}

}

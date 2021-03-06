package com.haystack.validation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.haystack.entities.Context;
import com.haystack.entities.Journey;
import com.haystack.entities.Meeting;

@Component("meetingSearchValidator")
public class MeetingSearchValidation implements Validator {
	
	private final Integer MAX_RAD = 10000;
	private final Integer MIN_RAD = 10;
	
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
			
			if (context.getLocation().getRad() > MAX_RAD ||
				context.getLocation().getRad() < MIN_RAD) {
				errors.rejectValue("contexts[0].location.rad",
						"invalidLocationRadius.meetingSearch.radius",
						"Invalid search radius");
			}
		}
		else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].journey.type",
					  "NotEmpty.meetingSearch.journeyType",
					  "Transport type is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].journey.company",
					  "NotEmpty.meetingSearch.journeyCompany",
					  "Transport company is required.");
			
			Journey journey = context.getJourney();
			
			if (journey.getStart() != null) {
				if (journey.getStart().getRad() > MAX_RAD ||
					journey.getStart().getRad() < MIN_RAD) {
					errors.rejectValue("contexts[0].journey.start.rad",
									   "invalidLocationRadius.meetingSearch.radius",
									   "Invalid search radius");
				}
			}
			if (journey.getEnd() != null) {
				if (journey.getEnd().getRad() > MAX_RAD ||
					journey.getEnd().getRad() < MIN_RAD) {
					errors.rejectValue("contexts[0].journey.end.rad",
									   "invalidLocationRadius.meetingSearch.radius",
									   "Invalid search radius");
				}
			}
		}
		
		try {
			
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
			
		} catch (IllegalArgumentException e) {
			errors.rejectValue("contexts[0].latestString",
					"invalidDate.meetingSearch.latest",
					"Invalid date format");
		}
		
	
	}

}

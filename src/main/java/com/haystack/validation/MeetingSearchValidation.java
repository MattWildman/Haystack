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
		Boolean usingLocation = meeting.getContexts().get(0)
								.getLocationType().equals("location");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
					  "NotEmpty.meetingSearch.title",
					  "Title is required.");
		
		if (usingLocation) {			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.title",
					  "NotEmpty.meetingSearch.locationTitle",
					  "Name is required.");		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.area",
					  "NotEmpty.meetingSearch.locationArea",
					  "Area is required.");		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.lat",
					  "NotEmpty.meetingSearch.locationLat",
					  "Latitude is required.");			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.longd",
					  "NotEmpty.meetingSearch.locationLongd",
					  "Longitude is required.");			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].location.rad",
					  "NotEmpty.meetingSearch.locationRad",
					  "Radius is required.");
		}
		else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contexts[0].journey.type",
					  "NotEmpty.meetingSearch.journeyType",
					  "Transport type is required.");		
		}
		
		//TODO Finish this!

	}

}

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<form:form method="Post" action="FindSomeone" commandName="meeting">
	
		<h1>Find someone</h1>
		Title: <form:input path="title" />
		<span class="error"><form:errors path="title" /></span><br>
		Summary: <form:textarea path="summary" />
		<span class="error"><form:errors path="summary" /></span><br>
		
		<h2>When did you meet?</h2>
		<input type="radio" id="no-range" name="use-range" value="no" checked="checked"/>I know the exact date<br>
		<input type="radio" id="yes-range" name="use-range" value="yes"/>I don't know the exact date (or it was for longer than a day)<br>
		
		<fieldset id="single-date-fields">
			On <input type="text" id="only-date" /> between <input id="earliest-time" size="2" value="12:00"  />
			and <input size="2" id="latest-time" value="12:00" />
		</fieldset>
		
		<fieldset id="date-range-fields" class="hidden">
			Between <input type="text" id="earliest-date" /> at <input id="range-earliest-time" size="2" value="12:00" /><br>
			and <input type="text" id="latest-date" /> at <input id="range-latest-time" size="2" value="12:00" />
		</fieldset>
		
		<span class="error"><form:errors path="contexts[0].earliestString" /></span>
		<span class="error"><form:errors path="contexts[0].latestString" /></span>
		<form:input id="earliest" type="hidden" path="contexts[0].earliestString" />
		<form:input id="latest" type="hidden" path="contexts[0].latestString" />
		
		<h2>How did you meet?</h2>	
		<form:select path="contexts[0].locationType" id="where-type">
			<form:option value="location">At a particular place</form:option>
			<form:option value="journey">On a journey</form:option>
		</form:select><br>
		
		<fieldset id="location-fields">
			<h3>The place</h3>
			Name: <form:input path="contexts[0].location.title" />
			<span class="error"><form:errors path="contexts[0].location.title" /></span><br>
			Area: <form:input path="contexts[0].location.area" />
			<span class="error"><form:errors path="contexts[0].location.area" /></span><br>
			Postcode: <form:input path="contexts[0].location.postcode" />
			<span class="error"><form:errors path="contexts[0].location.postcode" /></span><br>
			Country: <form:input path="contexts[0].location.country" />
			<span class="error"><form:errors path="contexts[0].location.country" /></span><br>
			<fieldset class="gllpLatlonPicker" id="location-map">
				<input type="text" class="gllpSearchField gllpLocationName" size="65">
				<input type="button" class="gllpSearchButton" value="search">
				<div class="gllpMap">Google Maps</div>
				<form:input type="hidden" path="contexts[0].location.lat" class="gllpLatitude" value="51"/>
				<form:input type="hidden" path="contexts[0].location.longd" class="gllpLongitude" value="0"/>
				<input type="hidden" class="gllpZoom" value="5"/>
			</fieldset>
			<span class="error"><form:errors path="contexts[0].location.lat" /></span><br>
			<span class="error"><form:errors path="contexts[0].location.longd" /></span><br>
			How accurate is this location?<br>
			<form:select path="contexts[0].location.rad">
				<form:option value="10">Within 20m - I know exactly where the meeting took place</form:option>
				<form:option value="200">Within 400m - search within a couple of streets</form:option>
				<form:option value="1000">Within 2km - a few streets or small town</form:option>
				<form:option value="5000">Within 10km - a large town centre</form:option>
				<form:option value="10000">Within 20km - search a large area!</form:option>
			</form:select>
		</fieldset>
		
		<fieldset id="journey-fields" class="hidden">
			<h3>The journey</h3>
			Type of transport: <form:input path="contexts[0].journey.type" />
			<span class="error"><form:errors path="contexts[0].journey.type" /></span><br>
			Transport company: <form:input path="contexts[0].journey.company" />
			<span class="error"><form:errors path="contexts[0].journey.company" /></span><br>
			Particular service: <form:input path="contexts[0].journey.service" />
			<span class="error"><form:errors path="contexts[0].journey.service" /></span><br>
	
			<h4>Where on the journey did you first meet?</h4>
			Name: <form:input path="contexts[0].journey.start.title" />
			<span class="error"><form:errors path="contexts[0].journey.start.title" /></span><br>
			Area: <form:input path="contexts[0].journey.start.area" />
			<span class="error"><form:errors path="contexts[0].journey.start.area" /></span><br>
			Postcode: <form:input path="contexts[0].journey.start.postcode" />
			<span class="error"><form:errors path="contexts[0].journey.start.postcode" /></span><br>
			Country: <form:input path="contexts[0].journey.start.country" />
			<span class="error"><form:errors path="contexts[0].journey.start.country" /></span><br>
			<fieldset class="gllpLatlonPicker" id="journey-start-map">
				<input type="text" class="gllpSearchField gllpLocationName" size="65">
				<input type="button" class="gllpSearchButton" value="search">
				<div class="gllpMap">Google Maps</div>
				<form:input type="hidden" path="contexts[0].journey.start.lat" class="gllpLatitude" value="51"/>
				<form:input type="hidden" path="contexts[0].journey.start.longd" class="gllpLongitude" value="0"/>
				<input type="hidden" class="gllpZoom" value="5"/>
			</fieldset>
			<span class="error"><form:errors path="contexts[0].journey.start.lat" /></span><br>
			<span class="error"><form:errors path="contexts[0].journey.start.longd" /></span><br>
			How accurate is this location?<br>
			<form:select path="contexts[0].journey.start.rad">
				<form:option value="10">Within 20m - I know exactly where we first met</form:option>
				<form:option value="200">Within 400m - search within a couple of streets</form:option>
				<form:option value="1000">Within 2km - a few streets or small town</form:option>
				<form:option value="5000">Within 10km - a large town centre</form:option>
				<form:option value="10000">Within 20km - search a large area!</form:option>
			</form:select>
			
			<h4>Where was the last place on the journey that you were together?</h4>
			Name: <form:input path="contexts[0].journey.end.title" />
			<span class="error"><form:errors path="contexts[0].journey.end.title" /></span><br>
			Area: <form:input path="contexts[0].journey.end.area" />
			<span class="error"><form:errors path="contexts[0].journey.end.area" /></span><br>
			Postcode: <form:input path="contexts[0].journey.end.postcode" />
			<span class="error"><form:errors path="contexts[0].journey.end.postcode" /></span><br>
			Country: <form:input path="contexts[0].journey.end.country" />
			<span class="error"><form:errors path="contexts[0].journey.end.country" /></span><br>
			<fieldset class="gllpLatlonPicker" id="journey-end-map">
				<input type="text" class="gllpSearchField gllpLocationName" size="65">
				<input type="button" class="gllpSearchButton" value="search">
				<div class="gllpMap">Google Maps</div>
				<form:input type="hidden" path="contexts[0].journey.end.lat" class="gllpLatitude" value="51"/>
				<form:input type="hidden" path="contexts[0].journey.end.longd" class="gllpLongitude" value="0"/>
				<input type="hidden" class="gllpZoom" value="5"/>
			</fieldset>
			<span class="error"><form:errors path="contexts[0].journey.end.lat" /></span><br>
			<span class="error"><form:errors path="contexts[0].journey.end.longd" /></span><br>
			How accurate is this location?<br>
			<form:select path="contexts[0].journey.end.rad">
				<form:option value="10">Within 20m - I know exactly where we were last together</form:option>
				<form:option value="200">Within 400m - search within a couple of streets</form:option>
				<form:option value="1000">Within 2km - a few streets or small town</form:option>
				<form:option value="5000">Within 10km - a large town centre</form:option>
				<form:option value="10000">Within 20km - search a large area!</form:option>
			</form:select>
		</fieldset>
		
		<h2>About you</h2>
		Name: 	<form:input path="user.title"/>
				<span class="error"><form:errors path="user.title" /></span><br>
		Gender:<br>
		<form:select path="user.gender">
			<form:option value="female">Female</form:option>
			<form:option value="male">Male</form:option>
		</form:select><br>
		Minimum age: <form:input path="user.minAge" />
					 <span class="error"><form:errors path="user.minAge" /></span><br>
		Maximum age: <form:input path="user.maxAge" />
					 <span class="error"><form:errors path="user.maxAge" /></span><br>
		Height: <form:input path="user.height" />
					 <span class="error"><form:errors path="user.height" /></span><br>
		Description: <form:textarea path="user.summary" />
					 <span class="error"><form:errors path="user.summary" /></span><br>
					 
		<h2>About them</h2>
		Name: 	<form:input path="participants[0].title"/>
				<span class="error"><form:errors path="participants[0].title" /></span><br>
		Gender:<br>
		<form:select path="participants[0].gender">
			<form:option value="female">Female</form:option>
			<form:option value="male">Male</form:option>
		</form:select><br>
		Minimum age: <form:input path="participants[0].minAge" />
					 <span class="error"><form:errors path="participants[0].minAge" /></span><br>
		Maximum age: <form:input path="participants[0].maxAge" />
					 <span class="error"><form:errors path="participants[0].maxAge" /></span><br>
		Height: <form:input path="participants[0].height" />
				<span class="error"><form:errors path="participants[0].height" /></span><br>
		Description: <form:textarea path="participants[0].summary" />
					 <span class="error"><form:errors path="participants[0].summary" /></span><br>
					 
		<input type="submit" value="Submit" />
					 
	</form:form>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="resources/js/date-time.js"></script>
	<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script src="resources/js/jquery-gmaps-latlon-picker.js"></script>
	
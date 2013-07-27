<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find someone</title>
</head>
<body>
	<form:form method="Post" action="findSomeone" commandName="meeting">
	
		<h1>Find someone</h1>
		Title: <form:input path="title" /><span class="error"><form:errors path="title" /></span><br>
		Summary: <form:textarea path="summary" /><span class="error"><form:errors path="summary" /></span><br>
		
		<h2>When did you meet?</h2>
		Earliest: <form:input type="date" path="contexts[0].earliest" /><span class="error"><form:errors path="contexts[0].earliest" /></span><br>
		Latest: <form:input type="date" path="contexts[0].latest" /><span class="error"><form:errors path="contexts[0].latest" /></span><br>
		
		<h2>Where did you meet?</h2>	
		<form:select path="contexts[0].locationType">
			<form:option value="location">At a particular place</form:option>
			<form:option value="journey">On a journey</form:option>
		</form:select><br>
		
		<h3>The place</h3>
		Name: <form:input path="contexts[0].location.title" /><span class="error"><form:errors path="contexts[0].location.title" /></span><br>
		Area: <form:input path="contexts[0].location.area" /><span class="error"><form:errors path="contexts[0].location.area" /></span><br>
		Postcode: <form:input path="contexts[0].location.postcode" /><span class="error"><form:errors path="contexts[0].location.postcode" /></span><br>
		Country: <form:input path="contexts[0].location.country" /><span class="error"><form:errors path="contexts[0].location.country" /></span><br>
		Latitude: <form:input path="contexts[0].location.lat" /><span class="error"><form:errors path="contexts[0].location.lat" /></span><br>
		Longitude: <form:input path="contexts[0].location.longd" /><span class="error"><form:errors path="contexts[0].location.longd" /></span><br>
		Radius: <form:input path="contexts[0].location.rad" /><span class="error"><form:errors path="contexts[0].location.rad" /></span><br>
		
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
		Latitude: <form:input path="contexts[0].journey.start.lat" />
		<span class="error"><form:errors path="contexts[0].journey.start.lat" /></span><br>
		Longitude: <form:input path="contexts[0].journey.start.longd" />
		<span class="error"><form:errors path="contexts[0].journey.start.longd" /></span><br>
		Radius: <form:input path="contexts[0].journey.start.rad" />
		<span class="error"><form:errors path="contexts[0].journey.start.rad" /></span><br>
		
		<h4>Where was the last place on the journey that you were together?</h4>
		Name: <form:input path="contexts[0].journey.end.title" />
		<span class="error"><form:errors path="contexts[0].journey.end.title" /></span><br>
		Area: <form:input path="contexts[0].journey.end.area" />
		<span class="error"><form:errors path="contexts[0].journey.end.area" /></span><br>
		Postcode: <form:input path="contexts[0].journey.end.postcode" />
		<span class="error"><form:errors path="contexts[0].journey.end.postcode" /></span><br>
		Country: <form:input path="contexts[0].journey.end.country" />
		<span class="error"><form:errors path="contexts[0].journey.end.country" /></span><br>
		Latitude: <form:input path="contexts[0].journey.end.lat" />
		<span class="error"><form:errors path="contexts[0].journey.end.lat" /></span><br>
		Longitude: <form:input path="contexts[0].journey.end.longd" />
		<span class="error"><form:errors path="contexts[0].journey.end.longd" /></span><br>
		Radius: <form:input path="contexts[0].journey.end.rad" />
		<span class="error"><form:errors path="contexts[0].journey.end.rad" /></span><br>
		
		<h2>About you</h2>
		Name: 	<form:input path="user.name"/>
				<span class="error"><form:errors path="user.name" /></span><br>
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
					 
		<h2>About them</h2>
		Name: 	<form:input path="participants[0].name"/>
				<span class="error"><form:errors path="participants[0].name" /></span><br>
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
					 
		<input type="submit" value="Submit" />
					 
	</form:form>
</body>
</html>
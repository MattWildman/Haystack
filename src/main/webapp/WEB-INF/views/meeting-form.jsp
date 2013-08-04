<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Find someone</title>
<script type="text/javascript">
	var ed, eM, ey, eh, em;
	var ld, lM, ly, lh, lm;
	function setDate(s) {
		switch (s) {
			case "ed" :
				ed = document.getElementById('e-day').value;
				break;
			case "eM" :
				eM = document.getElementById('e-month').value;
				break;
			case "ey" :
				ey = document.getElementById('e-year').value;
				break;
			case "eh" :
				eh = document.getElementById('e-hour').value;
				break;
			case "em" :
				em = document.getElementById('e-min').value;
				break;
			case "ld" :
				ld = document.getElementById('l-day').value;
				break;
			case "lM" :
				lM = document.getElementById('l-month').value;
				break;
			case "ly" :
				ly = document.getElementById('l-year').value;
				break;
			case "lh" :
				lh = document.getElementById('l-hour').value;
				break;
			case "lm" :
				lm = document.getElementById('l-min').value;
				break;
			default :
				break;
			}
				
			if (document.getElementById('no-range').checked) {
				ld = ed;
				lM = eM;
				ly = ey;
			}
			
			var earliest = new Date(ey, eM, ed, eh, em, 0, 0);
			var latest = new Date(ly, lM, ld, lh, lm, 0, 0);
			
			document.getElementById('earliest').value = earliest.toISOString();
			document.getElementById('latest').value = latest.toISOString();
		
	}
</script>
</head>
<body>
	<form:form method="Post" action="FindSomeone" commandName="meeting">
	
		<h1>Find someone</h1>
		Title: <form:input path="title" />
		<span class="error"><form:errors path="title" /></span><br>
		Summary: <form:textarea path="summary" />
		<span class="error"><form:errors path="summary" /></span><br>
		
		<h2>When did you meet?</h2>
		<input type="radio" id="no-range" name="use-range" value="no"/>I know the exact date<br>
		<input type="radio" id="yes-range" name="use-range" value="yes"/>I don't know the exact date<br>
		Earliest: <input name="e-day" id="e-day" size="2" onkeyup="setDate('ed')" /> 
				  <input name="e-month" id="e-month" size="2" onkeyup="setDate('eM')" /> 
				  <input name="e-year" id="e-year" size="4" onkeyup="setDate('ey')" />
				  <input name="e-hour" id="e-hour" size="2" onkeyup="setDate('eh')"/>:
				  <input name="e-min" id="e-min" size="2" onkeyup="setDate('em')"/>
				  <span class="error"><form:errors path="contexts[0].earliest" /></span><br>
		Earliest: <input name="l-day" id="l-day" size="2" onkeyup="setDate('ld')" /> 
				  <input name="l-month" id="l-month" size="2" onkeyup="setDate('lM')" /> 
				  <input name="l-year" id="l-year" size="4" onkeyup="setDate('ly')" />
				  <input name="l-hour" id="l-hour" size="2" onkeyup="setDate('lh')"/>:
				  <input name="l-min" id="l-min" size="2" onkeyup="setDate('lm')"/>
				  <span class="error"><form:errors path="contexts[0].latest" /></span><br>
		<form:input id="earliest" type="hidden" path="contexts[0].earliestString" />
		<form:input id="latest" type="hidden" path="contexts[0].latestString" />
		
		<h2>Where did you meet?</h2>	
		<form:select path="contexts[0].locationType">
			<form:option value="location">At a particular place</form:option>
			<form:option value="journey">On a journey</form:option>
		</form:select><br>
		
		<h3>The place</h3>
		Name: <form:input path="contexts[0].location.title" />
		<span class="error"><form:errors path="contexts[0].location.title" /></span><br>
		Area: <form:input path="contexts[0].location.area" />
		<span class="error"><form:errors path="contexts[0].location.area" /></span><br>
		Postcode: <form:input path="contexts[0].location.postcode" />
		<span class="error"><form:errors path="contexts[0].location.postcode" /></span><br>
		Country: <form:input path="contexts[0].location.country" />
		<span class="error"><form:errors path="contexts[0].location.country" /></span><br>
		Latitude: <form:input path="contexts[0].location.lat" />
		<span class="error"><form:errors path="contexts[0].location.lat" /></span><br>
		Longitude: <form:input path="contexts[0].location.longd" />
		<span class="error"><form:errors path="contexts[0].location.longd" /></span><br>
		Radius: <form:input path="contexts[0].location.rad" />
		<span class="error"><form:errors path="contexts[0].location.rad" /></span><br>
		
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
</body>
</html>
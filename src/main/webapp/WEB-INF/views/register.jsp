<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>Register on Haystack</h1>
<form:form method="Post" action="Register" commandName="registration">
	User name <span class="error"><form:errors path="username" /></span><br>
	<form:input size="50" path="username" /><br>
	Password <span class="error"><form:errors path="password" /></span><br>
	<form:password size="50" path="password" /><br>
	Confirm password <span class="error"><form:errors path="confirmPassword" /></span><br>
	<form:password size="50" path="confirmPassword" /><br>
	Email <span class="error"><form:errors path="email" /></span><br>
	<form:input size="50" type="email" path="email" /><br>
	<input type="submit" value="Submit" />
</form:form>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>Your account</h1>
<form:form action="Account" commandName="registration">
	User name<br>
	<form:input size="30" path="username" value="${user.username}" /><br>
	Email<br>
	<form:input size="30" path="email" value="${user.email}" /><br>
	Current password<br>
	<form:password size="30" path="password" /><br>
	New password<br>
	<form:password size="30" path="newPassword" /><br>
	Confirm new password<br>
	<form:password size="30" path="confirmPassword" /><br>
	<input type="submit" value="Update details" />
</form:form>
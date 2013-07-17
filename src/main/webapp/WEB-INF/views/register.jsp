<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register on Haystack</title>
</head>
<body>
	<form:form method="Post" action="register"
		commandName="registration">
		<table>
			<tr>
				<td>User Name:<span class="error"><form:errors
							path="username" /></span></td>
			</tr>
			<tr>
				<td><form:input path="username" /></td>
			</tr>
			<tr>
				<td>Password:<span class="error"><form:errors
							path="password" /></span></td>
			</tr>
			<tr>
				<td><form:password path="password" /></td>
			</tr>
			<tr>
				<td>Confirm Password:<span class="error"><form:errors
							path="confirmPassword" /></span></td>
			</tr>
			<tr>
				<td><form:password path="confirmPassword" /></td>
			</tr>
			<tr>
				<td>Email:<span class="error"><form:errors path="email" /></span></td>
			</tr>
			<tr>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
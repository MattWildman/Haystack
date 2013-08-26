<h1>Register on Haystack</h1>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<form:form method="Post" action="Register"
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
				<td><form:input type="email" path="email" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
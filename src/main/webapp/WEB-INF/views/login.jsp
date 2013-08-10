<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Login page</h1>

<p>
	<c:if test="${error == true}">
		<b class="error">Invalid username or password.</b>
	</c:if>
</p>

<form method="post" action="<c:url value='j_spring_security_check'/>">
	<table>
		<tbody>
			<tr>
				<td>Login:</td>
				<td><input type="text" name="j_username" id="j_username"
					size="30" maxlength="40" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="j_password" id="j_password"
					size="30" maxlength="32" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login" /></td>
			</tr>
		</tbody>
	</table>
</form>
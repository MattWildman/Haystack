<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Login to Haystack</h1>
<c:if test="${error == true}">
	<b class="error">Invalid username or password.</b>
</c:if>
<form method="post" action="<c:url value='j_spring_security_check'/>">
	Username<br><input type="text" name="j_username" id="j_username" size="30" maxlength="40" /><br>
	Password<br><input type="password" name="j_password" id="j_password" size="30" maxlength="32" /><br>
	<input type="submit" value="Login" />
</form>
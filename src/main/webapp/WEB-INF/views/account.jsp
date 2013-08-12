<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Your account</h1>
<form:form action="Account" commandName="user">
Username: <form:input path="username" value="${user.username})" />
Email: <form:input path="email" value="${user.email})" />
Current password: <form:password path="password" />
New password: <form:input path="password" />
Confirm new password: <form:input path="password" />
</form:form>
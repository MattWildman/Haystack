<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
  "http://www.w3.org/TR/html4/loose.dtd">
<c:url value="/resources/css/main.css" var="cssURL" />
<c:url value="/" var="homeURL" />
<c:url value="/Login" var="logInURL" />
<c:url value="/Register" var="registerURL" />
<c:url value="/j_spring_security_logout" var="logOutURL" />
<c:url value="/FindSomeone" var="findSomeoneURL" />
<c:url value="/Searches" var="searchesURL" />
<c:url value="/Inbox" var="inboxURL" />
<c:url value="/Account" var="accountURL" />
<c:url value="/Matches" var="matchesURL" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=uTF-8">

<title>Haystack - ${contentTitle}</title>
<link rel="stylesheet" type="text/css" href="${cssURL}" media="all">
</head>

<body>

	<ul class="main-nav">
		<li><a href='${homeURL}'>Home</a></li>
	</ul>
	<sec:authorize access="isAuthenticated()">
	<ul class="main-nav">
		<li><a href="${findSomeoneURL}">Find someone</a></li>
		<li><a href="${searchesURL}">Searches</a></li>
		<li><a href="${matchesURL}">Matches</a></li>
		<li><a href="${inboxURL}">Inbox</a></li>
		<li><a href="${accountURL}">Account</a></li>
	</ul>
	</sec:authorize>

	<div class="content">
		<p class="login-info">
		<sec:authorize access="isAnonymous()">
			<a href='${logInURL}'>Log in</a> or <a href="${registerURL}">register</a>.
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			Logged in as: 
			<sec:authentication property="principal.username" />. 
			<a href="${logOutURL}">Log out</a>.
		</sec:authorize>
		</p>
		<c:if test="${not empty contentBody}">
			<jsp:include page='${contentBody}' flush="true" />
		</c:if>
		<c:if test="${empty contentBody}">
			<jsp:include page="404.jsp" flush="true" />
		</c:if>
	</div>

	<%-- <jsp:include page="footer.jsp" flush="true" />--%>

</body>
</html>
<%@ page
	import="org.springframework.security.core.AuthenticationException"%>
<%@ page
	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException"%>
<%@ taglib prefix="authz"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Premium Bank</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="/PaymentServiceHub/views/css/bootstrap.min.css"
	rel="stylesheet">
<!--[if lt IE 9]>
			<script src="/PaymentServiceHub/views/js/libs/html5.js"></script>
		<![endif]-->
<link href="/PaymentServiceHub/views/css/styles.css" rel="stylesheet">
<!-- script references -->
<script src="/PaymentServiceHub/views/js/libs/jquery-2.0.3.min.js"></script>
<script src="/PaymentServiceHub/views/js/bootstrap.min.js"></script>
<script src="/PaymentServiceHub/views/js/main.js"></script>
</head>

<body>

	<div class="container">
		<h1>Sparklr</h1>

		<%
			if (session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) != null
					&& !(session
							.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) instanceof UnapprovedClientAuthenticationException)) {
		%>
		<div class="error">
			<h2>Woops!</h2>

			<p>
				Access could not be granted. (<%=((AuthenticationException) session
						.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY))
						.getMessage()%>)
			</p>
		</div>
		<%
			}
		%>
		<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />

		<authz:authorize ifAllGranted="ROLE_USER">
			<h2>Please Confirm</h2>

			<p>
				You hereby authorize "
				<c:out value="${client.clientId}" />
				" to access your protected resources.
			</p>

			<form id="confirmationForm" name="confirmationForm"
				action="<%=request.getContextPath()%>/oauth/authorize" method="post">
				<input name="user_oauth_approval" value="true" type="hidden" />
				<ul class="list-unstyled">
					<c:forEach items="${scopes}" var="scope">
						<c:set var="approved">
							<c:if test="${scope.value}"> checked</c:if>
						</c:set>
						<c:set var="denied">
							<c:if test="${!scope.value}"> checked</c:if>
						</c:set>
						<li>
							<div class="form-group">
								${scope.key}: <input type="radio" name="${scope.key}"
									value="true" ${approved}>Approve</input> <input type="radio"
									name="${scope.key}" value="false" ${denied}>Deny</input>
							</div>
						</li>
					</c:forEach>
				</ul>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<button class="btn btn-primary" type="submit">Submit</button>
			</form>

		</authz:authorize>

		<div class="footer">
			Sample application for <a
				href="http://github.com/spring-projects/spring-security-oauth"
				target="_blank">Spring Security OAuth</a>
		</div>

	</div>

</body>
</html>

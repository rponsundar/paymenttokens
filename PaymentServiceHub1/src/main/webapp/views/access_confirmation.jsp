<%@ page
	import="org.springframework.security.core.AuthenticationException"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContext"%>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ page
	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException"%>
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
<script src="/PaymentServiceHub/views/js/libs/angular.min.js"></script>
<script src="/PaymentServiceHub/views/js/libs/angular-resource.min.js"></script>
<script src="/PaymentServiceHub/views/js/bootstrap.min.js"></script>
<script src="/PaymentServiceHub/views/js/main.js"></script>
</head>

<body ng-app="myBank" ng-controller="URNContoller">

	<input type="hidden" id="userId" name="userId" value="<%=((SecurityContext) session
					.getAttribute("SPRING_SECURITY_CONTEXT"))
					.getAuthentication().getName()%>"/>

	<div class="container">
		<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />

		<!--URN modal-->
		<div id="urnModal" class="modal show" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h1 class="text-center">Premium Banking</h1>
					</div>
					<div class="modal-body">
						<h4><%=((SecurityContext) session
					.getAttribute("SPRING_SECURITY_CONTEXT"))
					.getAuthentication().getName()%>, generate Payment Token for
							processing...
						</h4>
						<h1></h1>
						<div class="form-group">
							<select id="cardNum" name="cardNum" class="form-control input-sm"
								required ng-model="car.cardNum"
								ng-options="cardNum for cardNum in custCards">
								<option value="">Select PAN NO</option>
							</select> <span style="color: red"
								ng-show="urnForm.cardNum.$dirty && urnForm.cardNum.$invalid">
								<span ng-show="urnForm.cardNum.$error.required">PAN
									number is required</span>
							</span>
						</div>
						<div class="form-group">
							<input type="text" class="form-control input-sm" id="expDate" name="expDate"
								placeholder="Expiry Date (MM/YYYY)" ng-model="car.expDate"
								maxlength="7" required> <span style="color: red"
								ng-show="urnForm.expDate.$dirty && urnForm.expDate.$invalid">
								<span ng-show="urnForm.expDate.$error.required">Exp Date
									is required</span>
							</span>
						</div>

						<div class="form-group">
							<input id="amount" name="amount" type="text" class="form-control input-sm"
								placeholder="Amount" ng-model="car.amount" maxlength="4">
						</div>
						<div class="form-group" align='right'>
							<button id="genbutton" class="btn btn-small btn-primary"
								ng-click="generateUrn(car)">Generate</button>
						</div>


						<div class="form-group">
							<form name="urnForm" id="urnForm"
								action="<%=request.getContextPath()%>/oauth/authorize"
								method="post">
								<h4><%=((SecurityContext) session
					.getAttribute("SPRING_SECURITY_CONTEXT"))
					.getAuthentication().getName()%>, authorize the vendor to use
									token...
								</h4>

								<p>
									You hereby authorize "
									<c:out value="${authorizationRequest.clientId}" />
									" to access your protected resources.
								</p>

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
												<input type="radio" name="${scope.key}" value="true"
													${approved}>Approve</input> <input type="radio"
													name="${scope.key}" value="false" ${denied}>Deny</input>
											</div>
										</li>
									</c:forEach>
								</ul>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<button class="btn btn-primary" type="submit">Submit</button>
						</div>
						</form>
					</div>


				</div>


			</div>
		</div>
	</div>
</body>
</html>

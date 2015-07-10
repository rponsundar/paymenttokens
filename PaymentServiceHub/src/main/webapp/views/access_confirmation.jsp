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

	<input type="hidden" id="userId" name="userId"
		value="<%=((SecurityContext) session
					.getAttribute("SPRING_SECURITY_CONTEXT"))
					.getAuthentication().getName()%>" />
	<input type="hidden" id="clientId" name="clientId"
		value="${authorizationRequest.clientId}" />

	<input type="hidden" id="selectedURN" ng-model="selectedURN" />

	<div class="container">
		<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />

		<!--URN modal-->
		<div id="urnModal" class="modal show" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">x</button>
						<h1 class="text-center">Premium Banking</h1>
					</div>
					<div class="modal-body">
						<span><h4>
								Dear
								<%=((SecurityContext) session
					.getAttribute("SPRING_SECURITY_CONTEXT"))
					.getAuthentication().getName()%>
							</h4>Please choose/generate Payment Token for processing...</span>

						<div class="form-group">
							<table st-table="tokenCollection" class="table">
								<thead>
									<tr>
										<th></th>
										<th st-sort="PAN_Number">Card Number</th>
										<th st-sort="URN">URN</th>
										<th st-sort="Amount">Amount</th>
										<th st-sort="Expiry_Date">Expiry Date</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="token in tokenCollection">
										<td><input type="radio" id="{{token.urn}}"
											value="{{token.urn}}" name="tokenselect"
											onchange="changeTokenSelection('{{token.urn}}')" /></td>
										<td>{{token.cardNumber | uppercase}}</td>
										<td>{{token.urn}}</td>
										<td>{{token.amount}}</td>
										<td>{{token.expiryDate | date}}</td>
									</tr>
									<tr>
										<td><input type="radio" id="new_URN" value="new_URN"
											name="tokenselect" onchange="changeTokenSelection('new_URN')" /></td>
										<td colspan="4">Generate new Payment Token</td>
									</tr>
									<tr>
										<td />
										<td colspan="4">
											<div id="newToken" class="form-group"
												ng-show="selectedURN == 'new_URN'">
												<select id="cardNum" name="cardNum"
													class="form-control input-sm" required
													ng-model="car.cardNum"
													ng-options="cardNum for cardNum in custCards">
													<option value="">Select PAN NO</option>
												</select> <span style="color: red"
													ng-show="urnForm.cardNum.$dirty && urnForm.cardNum.$invalid">
													<span ng-show="urnForm.cardNum.$error.required">PAN
														number is required</span>
												</span> <input type="text" class="form-control input-sm"
													id="expDate" name="expDate"
													placeholder="Expiry Date (MM/YYYY)" ng-model="car.expDate"
													maxlength="7" required> <span style="color: red"
													ng-show="urnForm.expDate.$dirty && urnForm.expDate.$invalid">
													<span ng-show="urnForm.expDate.$error.required">Exp
														Date is required</span>
												</span> <input id="amount" name="amount" type="text"
													class="form-control input-sm" placeholder="Amount"
													ng-model="car.amount" maxlength="4">
											</div>

											<div class="form-group" align='right'>
												<button id="genbutton" class="btn btn-small btn-primary"
													ng-click="generateUrn(car)">Generate</button>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<hr />
						<div class="form-group">
							<form name="urnForm" id="urnForm"
								action="<%=request.getContextPath()%>/oauth/authorize"
								method="post">

								<p>
									You hereby authorize "<span>{{clientDetail.clientName}}</span>"
									to access your protected resources.
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
									</c:forEach>
								</ul>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<button class="btn btn-success" type="submit" name="scope.read"
									value="true">Authorize</button>
								<button class="btn btn-danger" type="submit" name="scope.read"
									value="false">Deny</button>
						</div>
						</form>
					</div>

					<script type="text/javascript">
						function changeTokenSelection(value) {

							$("#selectedURN").val(value);
							if (value == 'new_URN') {
								$("#newToken").show('slow');
								$("#genbutton").html('Generate');
							} else {
								$("#newToken").hide('slow');
								$("#genbutton").html('Choose');
							}
						};
					</script>

				</div>


			</div>
		</div>
	</div>
</body>
</html>

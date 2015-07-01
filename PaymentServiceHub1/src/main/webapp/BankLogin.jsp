<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href="/PaymentServiceHub/views/css/bootstrap.min.css"
		rel="stylesheet">
		<link href="/PaymentServiceHub/views/css/styles.css" rel="stylesheet">
			<!-- script references -->
			<script src="/PaymentServiceHub/views/js/libs/jquery-2.0.3.min.js"></script>
			<script src="/PaymentServiceHub/views/js/libs/angular.min.js"></script>
			<script src="/PaymentServiceHub/views/js/libs/angular-route.min.js"></script>
			<script
				src="/PaymentServiceHub/views/js/libs/angular-resource.min.js"></script>
			<script src="/PaymentServiceHub/views/js/bootstrap.min.js"></script>
			<script src="/PaymentServiceHub/views/js/main.js"></script>
			<title>Premium Login</title>
</head>

<body>


	<div id="loginModal" class="modal show" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h1 class="text-center">Login to Premium Banking</h1>
				</div>
				<div id="content">
					<c:if test="${not empty param.authentication_error}">
						<div class="alert alert-danger" role="alert">
							<strong>Authentication Error!</strong>  Your login attempt was not
							successful.
						</div>
					</c:if>
					<c:if test="${not empty param.authorization_error}">
						<div class="alert alert-warning" role="alert">
							<strong>Authorization Error!</strong> You are not permitted to
							access that resource.
						</div>
					</c:if>
				</div>
				<div class="modal-body">
					<form class="form col-md-12 center-block" id="loginForm"
						name="loginForm" action="BankLogin.jsp" method="post">
						<div class="form-group">
							<input type="text" class="form-control input-lg"
								placeholder="User ID" name="username">
						</div>
						<div class="form-group">
							<input type="password" class="form-control input-lg"
								placeholder="Password" name='password'>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-primary btn-lg btn-block"
								value="Login" />
						</div>
					</form>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>

</body>
</html>
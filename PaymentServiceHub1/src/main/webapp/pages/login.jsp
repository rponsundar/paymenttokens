<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<title>Simple REST</title>
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
					<h1>Authentication Error</h1>
					<p class="error">Your login attempt was not successful.</p>
				</c:if>
				<c:if test="${not empty param.authorization_error}">
					<h1>Authorization Error</h1>
					<p class="error">You are not permitted to access that resource.</p>
				</c:if>
			</div>
			<div class="modal-body">
				<form class="form col-md-12 center-block" id="loginForm" name="loginForm" action="j_spring_security_check" method="post">
					<div class="form-group">
						<input type="text" class="form-control input-lg"
							placeholder="User ID" name="j_username">
					</div>
					<div class="form-group">
						<input type="password" class="form-control input-lg"
							placeholder="Password" name='j_password'>
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-primary btn-lg btn-block">Login</input>
					</div>
				</form>
			</div>
			<div class="modal-footer"></div>
		</div>
	</div>
</div>

</body>
</html>
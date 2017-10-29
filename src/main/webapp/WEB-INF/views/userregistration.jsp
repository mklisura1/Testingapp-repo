<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<!DOCTYPE html>
<html lang="en">

<head>
<title>BP | Registration</title>

<!-- Import pocetnih skripti iz fajla -->
<%@ include file="page_tiles/start_skripts.jsp"%>

</head>
<body class="hold-transition register-page">
	<div class="register-box">
		<div class="register-logo">
			<a href="login"><b>B</b>Photo</a>
		</div>

		<div class="register-box-body">
			<p class="login-box-msg">Register a new membership</p>
			<form action="${cp}/users/registration" method="post">
				<div class="form-group has-feedback">
					<input type="text" class="form-control" value="${username}"
						placeholder="Username" name="username"> <span
						class="glyphicon glyphicon-user form-control-feedback"></span>
					<h5 style="color: red">${errorUsername}</h5>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" placeholder="Password"
						name="password"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
					<h5 style="color: red">${error}</h5>
					<h5 style="color: red">${errorLength}</h5>
					<h5 style="color: red">${errorLetters}</h5>
					<h5 style="color: red">${errorNumbers}</h5>
					<h5 style="color: red">${errorSpecialCharacters}</h5>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" name="rePassword"
						placeholder="Retype password"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
					<h5 style="color: red">${errorRePass}</h5>
				</div>
				<div class="form-group has-feedback">
					<input type="text" class="form-control" name="mobilePhone"
						placeholder="e.g. 062-954-098"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
					<h5 style="color: red">${errorMobilePhone}</h5>
				</div>
				<div class="form-group has-feedback">
					<input type="text" class="form-control" name="email"
						placeholder="e.g. example@example.com"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
					<h5 style="color: red">${errorEmail}</h5>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<a href="${cp}/login" class="text-center">I already have
							a membership</a>
					</div>

					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
					</div>
					<!-- /.col -->
				</div>
			</form>
		</div>
		<!-- /.form-box -->
	</div>
	<!-- /.register-box -->

	<!-- Import krajnjih skripti iz fajla -->
	<%@ include file="page_tiles/end_skripts.jsp"%>

</body>
</html>

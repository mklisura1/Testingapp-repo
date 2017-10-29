<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<!DOCTYPE html>
<html>
<head>
<title>BP | Search friends</title>

<!-- Import pocetnih skripti iz fajla -->
<%@ include file="page_tiles/start_skripts.jsp"%>

</head>
<body class="skin-blue sidebar-mini">
	<div class="wrapper ">

		<!-- Import main-header iz fajla -->
		<%@ include file="page_tiles/main_header.jsp"%>
		
		<!-- Import main-header iz fajla -->
		<jsp:include page="page_tiles/main_sidebar.jsp">
			<jsp:param value="active" name="paramFriendsActive"/>
			<jsp:param value="active" name="paramFriendsAddActive"/>
		</jsp:include>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Search for friends</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li>Friends</li>
					<li class="active">Search result</li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-12">

						<!-- Search dio -->
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">Search result</h3>
							</div>
							<div class="box-body">
								<c:forEach var="users" items="${users_result}">
									<div class="col-md-3">

										<!-- Profile Image -->
										<div class="box box-primary">
											<div class="box-body box-profile">
												<img class="profile-user-img img-responsive img-circle"
													src="${cp}/resources/images/user2-128x128.jpg"
													alt="User profile picture">
												<h3 class="profile-username text-center">${users.userDetails.first_name} ${users.userDetails.last_name}</h3>

												<ul class="list-group list-group-unbordered">
													<li class="list-group-item"><b>Photos</b> <a
														class="pull-right">${fn:length(users.galleries)}</a></li>
													<li class="list-group-item"><b>Friends</b> <a
														class="pull-right">${fn:length(users.friends)}</a></li>
												</ul>

												<a href="${cp}/users/add/friend/${users.user_id}" class="btn btn-primary btn-block"><b>Add friend</b></a>
											</div>
											<!-- /.box-body -->
										</div>
										<!-- /.box -->
									</div>
										
								</c:forEach>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<!-- Import footera iz fajla -->
		<%@ include file="page_tiles/footer.jsp"%>
	</div>
	
	<!-- Import krajnjih skripti iz fajla -->
	<%@ include file="page_tiles/end_skripts.jsp"%>

</body>
</html>

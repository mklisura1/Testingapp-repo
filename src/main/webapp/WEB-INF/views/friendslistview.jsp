<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<!DOCTYPE html>
<html>
<head>
<title>BP | My friends</title>

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
			<jsp:param value="active" name="paramFriendsListActive"/>
		</jsp:include>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>My friends</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li>Friends</li>
					<li class="active">My friends</li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-12">

						<!-- Search dio -->
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">My friends</h3>
							</div>
							<div class="box-body">
								<c:forEach var="friends" items="${friends}">
									<div class="col-md-3">

										<!-- Profile Image -->
										<div class="box box-primary">
											<div class="box-body box-profile">
												<img class="profile-user-img img-responsive img-circle"
													src="${cp}/resources/images/user2-128x128.jpg"
													alt="User profile picture">
												<h3 class="profile-username text-center">${friends.userDetails.first_name} ${users.userDetails.last_name}</h3>

												<ul class="list-group list-group-unbordered">
													<li class="list-group-item"><b>Photos</b> <a
														class="pull-right">${fn:length(friends.galleries)}</a></li>
													<li class="list-group-item"><b>Friends</b> <a
														class="pull-right">${fn:length(friends.friends)}</a></li>
												</ul>
												
												<a href="${cp}/users/view/friend/${friends.user_id}" class="btn btn-info btn-block"><b>View profile</b></a>
												<a href="${cp}/users/remove/friend/${friends.user_id}" class="btn btn-primary btn-block"><b>Remove friend</b></a>
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<!DOCTYPE html>
<html>
<head>
<title>BP | Home</title>

<!-- Import pocetnih skripti iz fajla -->
<%@ include file="page_tiles/start_skripts.jsp"%>

</head>
<body class="skin-blue layout-top-nav">
	<!-- Site wrapper -->
	<div class="wrapper">
		<!-- Import main-header iz fajla -->
		<%@ include file="page_tiles/main_header.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Home Page</h1>
			</section>

			<!-- Main content -->
			<section class="content">

				<!-- Default box -->
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">Home</h3>						
					</div>
					<div class="box-body">
						<!-- ///////////-------SADRZAJ STRANICE---------////////////// -->
						<h4>BPhoto welcome!</h4>
						<h4>"${cp}"</h4>
						<img src="${cp}/resources/images/user2-160x160-1.jpg" class="user-image" alt="User Image">
						
						<!-- ///////////-------SADRZAJ STRANICE---------////////////// -->
					</div>				
				</div>
				<!-- /.box -->

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
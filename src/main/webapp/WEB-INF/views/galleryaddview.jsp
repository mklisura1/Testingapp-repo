<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>BP | Add gallery</title>

<!-- Import pocetnih skripti iz fajla -->
<%@ include file="page_tiles/start_skripts.jsp"%>

</head>
<body class="skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Import main-header iz fajla -->
		<%@ include file="page_tiles/main_header.jsp"%>
		
		<!-- Import main-header iz fajla -->
		<jsp:include page="page_tiles/main_sidebar.jsp">
			<jsp:param value="active" name="paramGalleryActive"/>
			<jsp:param value="active" name="paramGalleryAddActive"/>
		</jsp:include>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Add new gallery</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li>Gallery</li>
					<li class="active">Add gallery</li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-4">

						<!-- Profile Image -->
						<div class="box box-primary">
							<div class="box-body ">
								<form:form class="form-horizonata" role ="form" method="POST" action="${cp}/gallery/add" modelAttribute="gallery">
								
									<spring:bind path="gallery_name">
				        				<div class="form-group ${status.error ? 'has-error' : ''}">
						                    <form:label path="gallery_name">Gallery</form:label>
			                    			<form:input type="text" path="gallery_name" placeholder="eg. First gallery"  class="form-control"/>
			                    			<div class="has-error">
												<form:errors path="gallery_name" cssStyle="color: #ff0000;"/>
											</div>
						                </div>
									</spring:bind>
									
									<spring:bind path="gallery_description">
				        				<div class="form-group ${status.error ? 'has-error' : ''}">
						                    <form:label path="gallery_description">Gallery description</form:label>
			                    			<form:input type="text" path="gallery_description" placeholder="eg. First gallery description"  class="form-control"/>
			                    			<div class="has-error">
												<form:errors path="gallery_description" cssStyle="color: #ff0000;"/>
											</div>
						                </div>
									</spring:bind>
									
									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<button type="submit" class="btn btn-danger">Save</button>
										</div>
										<br>
									</div>

								</form:form>
								
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

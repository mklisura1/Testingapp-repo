<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>BP | Show galleries</title>

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
			<jsp:param value="active" name="paramGalleryListActive"/>
		</jsp:include>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Show all galleries</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li>Gallery</li>
					<li class="active">Show galleries</li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-6">

						<!-- Profile Image -->
						<div class="box box-primary">
							<div class="box-body ">
								<div id="example1_wrapper"
									class="dataTables_wrapper form-inline dt-bootstrap">
									<div class="row">
										<div class="col-sm-12">
											<table class="table table-bordered table-striped dataTable">
												<thead>
													<tr>
														<th>Gallery name</th>
														<th>Description</th>
														<th>Map</th>
														<th style="width: 165px;">Action</th>
														
													</tr>
												</thead>
												<tbody>
													<c:forEach var="gallery" items="${galleries}">
														<tr>															
															<td>${gallery.gallery_name}</td>
															<td>${gallery.gallery_description}</td>
															<td><a href="${cp}/gallery/show/map">Show pictures on map</a></td>
															<td>
															
															<form:form method="post" action="${cp}/gallery/${gallery.gallery_id}/add/picture" enctype="multipart/form-data">
																<table>
																	<tr>
																		<td>
																			<input type="file" name="file" id="file"></input>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<input class="btn btn-primary" type="submit" value="Add new picture" />
																		</td>
																	</tr>
																</table>
															</form:form>

																
															</td>
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>

								</div>
								
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

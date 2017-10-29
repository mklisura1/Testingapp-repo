<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>BP | Friend profile</title>

<!-- Import pocetnih skripti iz fajla -->
<%@ include file="page_tiles/start_skripts.jsp"%>


<!-- Magnific Popup core CSS file -->
<link rel="stylesheet" href="${cp}/resources/plugins/magnific-popup/magnific-popup.css">

<style type="text/css">
	.image-link {
  cursor: -webkit-zoom-in;
  cursor: -moz-zoom-in;
  cursor: zoom-in;
}


/* This block of CSS adds opacity transition to background */
.mfp-with-zoom .mfp-container,
.mfp-with-zoom.mfp-bg {
	opacity: 0;
	-webkit-backface-visibility: hidden;
	-webkit-transition: all 0.3s ease-out; 
	-moz-transition: all 0.3s ease-out; 
	-o-transition: all 0.3s ease-out; 
	transition: all 0.3s ease-out;
}

.mfp-with-zoom.mfp-ready .mfp-container {
		opacity: 1;
}
.mfp-with-zoom.mfp-ready.mfp-bg {
		opacity: 0.8;
}

.mfp-with-zoom.mfp-removing .mfp-container, 
.mfp-with-zoom.mfp-removing.mfp-bg {
	opacity: 0;
}



/* padding-bottom and top for image */
.mfp-no-margins img.mfp-img {
	padding: 0;
}
/* position of shadow behind the image */
.mfp-no-margins .mfp-figure:after {
	top: 0;
	bottom: 0;
}
/* padding for main container */
.mfp-no-margins .mfp-container {
	padding: 0;
}



/* aligns caption to center */
.mfp-title {
  text-align: center;
  padding: 6px 0;
}
.image-source-link {
  color: #DDD;
}

</style>

</head>
<body class="skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Import main-header iz fajla -->
		<%@ include file="page_tiles/main_header.jsp"%>
		
		<!-- Import main-header iz fajla -->
		<jsp:include page="page_tiles/main_sidebar.jsp">
			<jsp:param value="active" name="paramProfileActive"/>
		</jsp:include>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Friend profile</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li>Friends</li>
					<li class="active">Friend profile</li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				
				<!-- Info boxes -->
				<div class="row">
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-aqua"><i
								class="ion ion-ios-camera-outline"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Galleries</span> 
								<span class="info-box-number">90</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-red"><i
								class="ion ion-navigate"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Places</span> 
								<span class="info-box-number">41,410</span>
								<a href="#" class="info-box-number">View</a>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->

					<!-- fix for small devices only -->
					<div class="clearfix visible-sm-block"></div>

					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-green"><i
								class="ion ion-ios-pulse-strong"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">New</span> 
								<span class="info-box-number">760</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-yellow"><i
								class="ion ion-ios-people-outline"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Friends</span> 
								<span class="info-box-number">2,000</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
				</div>

				<div class="row">
					<div class="col-md-3">

						<!-- Profile Image -->
						<div class="box box-primary">
							<div class="box-body box-profile">
								<img class="profile-user-img img-responsive img-circle"
									src="${cp}/resources/images/user2-128x128.jpg"
									alt="User profile picture">
								<h3 class="profile-username text-center">${userDetails.first_name} ${userDetails.last_name}</h3>

								<ul class="list-group list-group-unbordered">
									<li class="list-group-item"><b>Photos</b> <a
										class="pull-right">${photos_size}</a></li>
									<li class="list-group-item"><b>Friends</b> <a
										class="pull-right">${friends_size}</a></li>
								</ul>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						<!-- About Me Box -->
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">About Me</h3>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<strong><i class="fa fa-calendar-check-o margin-r-5"></i>Birth date</strong>
								<p class="text-muted"><fmt:formatDate type="date" pattern="dd.MM.yyyy" value="${userDetails.birth_date}" /></p>

								<hr>

								<strong><i class="fa fa-map-marker margin-r-5"></i>
									Location</strong>
								<p class="text-muted">${userDetails.address}</p>

								<hr>

								<strong><i class="fa fa-phone margin-r-5"></i>
									Mobile phone</strong>
								<p>${userDetails.mobile_phone}</p>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
					<div class="col-md-9">
						<div class="nav-tabs-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#photo" data-toggle="tab">Photos</a></li>
								<li><a href="#settings" data-toggle="tab">Settings</a></li>
							</ul>
							<div class="tab-content">
								<div class="active tab-pane" id="photo">

									<!-- Post -->
									<div class="post">
										<div class="user-block">
											<img class="img-circle img-bordered-sm"
												src="${cp}/resources/images/user2-128x128.jpg" alt="user image">
											<span class="username"> <a href="#">Adam Jones</a> <a
												href="#" class="pull-right btn-box-tool"><i
													class="fa fa-times"></i></a>
											</span> <span class="description">Posted 5 photos - 5 days
												ago</span>
										</div>
										<!-- /.user-block -->
										<div class="row margin-bottom">
											<c:forEach var="pictures" items="${pictures}">
												<div class="col-sm-6">
													<a href="${cp}/gallery/get/image/${pictures.picture_id}" data-source="${cp}/gallery/show/image/${pictures.picture_id}" class="with-caption image-link" title="">
														<img class="img-responsive" src="${cp}/gallery/get/image/${pictures.picture_id}" alt="Photo">
													</a>
													<ul class="list-inline">
														<li><a href="${cp}/gallery/like/image/${pictures.picture_id}" class="link-black text-sm"><i
																class="fa fa-thumbs-o-up margin-r-5"></i> Like</a></li>
													</ul>
												</div>
											</c:forEach>
										</div>
									</div>
									<!-- /.post -->
								</div>
								<!-- /.tab-pane -->
								
								<div class="tab-pane" id="settings">
									<form:form class="form-horizonata" role ="form" method="POST" action="${cp}/users/profile/update" modelAttribute="userDetails">
										
										
										<spring:bind path="first_name">
				        					<div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="first_name">First name</form:label>
			                    				<form:input type="text" path="first_name" placeholder="eg. John"  class="form-control"/>
			                    				<div class="has-error">
													<form:errors path="first_name" cssStyle="color: #ff0000;"/>
												</div>
						                    </div>
										</spring:bind>
										
										<spring:bind path="last_name">
				        					<div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="last_name">Last name</form:label>
			                    				<form:input type="text" path="last_name" placeholder="eg. Doe"  class="form-control"/>
			                    				<div class="has-error">
													<form:errors path="last_name" cssStyle="color: #ff0000;"/>
												</div>
						                    </div>
										</spring:bind>
										
										<spring:bind path="birth_date">
						                    <div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="birth_date">Birth date</form:label>
						                    	<form:input type="text" class="form-control" path="birth_date" value="${birth_date}" id="datepicker" />
						                    	<div class="has-error">
													<form:errors path="birth_date" cssStyle="color: #ff0000;"/>
												</div>
											</div>
										</spring:bind>
										
										<spring:bind path="email">
				        					<div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="email">Email</form:label>
			                    				<form:input type="text" path="email" placeholder="eg. example@example.com"  class="form-control"/>
			                    				<div class="has-error">
													<form:errors path="email" cssStyle="color: #ff0000;"/>
												</div>
						                    </div>
										</spring:bind>
										
										<spring:bind path="mobile_phone">
				        					<div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="mobile_phone">Mobile phone</form:label>
			                    				<form:input type="text" path="mobile_phone" placeholder="eg. 38762123456"  class="form-control"/>
			                    				<div class="has-error">
													<form:errors path="mobile_phone" cssStyle="color: #ff0000;"/>
												</div>
						                    </div>
										</spring:bind>
										
										<spring:bind path="country">
				        					<div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="country">Country</form:label>
			                    				<form:input type="text" path="country" placeholder="eg. Bosna i Hercegovina"  class="form-control"/>
			                    				<div class="has-error">
													<form:errors path="country" cssStyle="color: #ff0000;"/>
												</div>
						                    </div>
										</spring:bind>
										
										<spring:bind path="address">
				        					<div class="form-group ${status.error ? 'has-error' : ''}">
						                    	<form:label path="address">Address</form:label>
			                    				<form:input type="text" path="address" placeholder="eg. Branilaca Sarajeva 2"  class="form-control"/>
			                    				<div class="has-error">
													<form:errors path="address" cssStyle="color: #ff0000;"/>
												</div>
						                    </div>
										</spring:bind>
										
									</form:form>
								</div>
								<!-- /.tab-pane -->
							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.nav-tabs-custom -->
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
	
	<!-- Magnific Popup core JS file -->
	<script src="${cp}/resources/plugins/magnific-popup/jquery.magnific-popup.js"></script>
	
	<!-- Skripta za galeriju -->
	<script type="text/javascript">

		$('.without-caption').magnificPopup({
			type: 'image',
			closeOnContentClick: true,
			closeBtnInside: false,
			mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
			image: {
				verticalFit: true
			},
			zoom: {
				enabled: true,
				duration: 300 // don't foget to change the duration also in CSS
			}
		});

		

	$('.with-caption').magnificPopup({
			type: 'image',
			gallery: {
				  enabled: true, // set to true to enable gallery

				  navigateByImgClick: true,

				  arrowMarkup: '<button title="%title%" type="button" class="mfp-arrow mfp-arrow-%dir%"></button>', // markup of an arrow button

				  tPrev: 'Previous (Left arrow key)', // title for left button
				  tNext: 'Next (Right arrow key)', // title for right button
				  tCounter: '<span class="mfp-counter">%curr% of %total%</span>' // markup of counter
				},
			closeOnContentClick: true,
			closeBtnInside: false,
			mainClass: 'mfp-with-zoom mfp-img-mobile',
			image: {
				verticalFit: true,
				titleSrc: function(item) {
					return item.el.attr('title') + ' &middot; <a class="image-source-link" href="'+item.el.attr('data-source')+'" target="_blank">image source</a>';
				}
			},
			zoom: {
				enabled: true
			}
		});
			

	</script>

</body>
</html>

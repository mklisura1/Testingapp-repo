<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
		<meta charset="utf-8">
		<title>Images pinned</title>
		
		
		<!-- Import pocetnih skripti iz fajla -->
		<%@ include file="page_tiles/start_skripts.jsp"%>
		
		<style>
			/* Always set the map height explicitly to define the size of the div element that contains the map. */
			#map {
				height: 100%;
			}
			/* Optional: Makes the sample page fill the window. */
			html, body {
				height: 100%;
				margin: 0;
				padding: 0;
			}
		</style>
		
		<!-- Import krajnjih skripti iz fajla -->
		<%@ include file="page_tiles/end_skripts.jsp"%>
		
		<!-- Skripta za mapu zajedno sa API-jem od Google-a -->
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAOAgekGr0cg6NJEYMmME1wVps3NAmprro"></script>
		
	</head>
	<body>
	
		<div id="map"></div>
		

	</body>
	
	
	<script type="text/javascript">

	
	var MapPoints = '[{"address":{"address":"plac Grzybowski, Warszawa, Polska","lat":"52.2360592","lng":"21.002903599999968"},"title":"Warszawa"},{"address":{"address":"Jana Paw\u0142a II, Warszawa, Polska","lat":"52.2179967","lng":"21.222655600000053"},"title":"Wroc\u0142aw"},{"address":{"address":"Wawelska, Warszawa, Polska","lat":"52.2166692","lng":"20.993677599999955"},"title":"O\u015bwi\u0119cim"}]';

	var MY_MAPTYPE_ID = 'custom_style';
	var directionsDisplay;
	var directionsService = new google.maps.DirectionsService();
	var map;

	function initialize() {
	    directionsDisplay = new google.maps.DirectionsRenderer({suppressMarkers:true});

	    if (jQuery('#map').length > 0) {

	        //var locations = jQuery.parseJSON(MapPoints);

	        map = new google.maps.Map(document.getElementById('map'), {
	            mapTypeId: google.maps.MapTypeId.ROADMAP,
	            scrollwheel: false
	        });
	        directionsDisplay.setMap(map);
	        
	        var infowindow = new google.maps.InfoWindow();
	        
	        var flightPlanCoordinates = [];
	        var bounds = new google.maps.LatLngBounds();
	        
	        var ctx = "${pageContext.request.servletContext.contextPath}"
		
			$.ajax({
				datatype:"json",
				type : "GET",
				url : "" + ctx + "/users/5/get/locations",
				async: false,
				success : function(response) {
					var jsonData = JSON.parse(response);
		
					console.log(response);
		
					$.each(jsonData, function (i, item) {
		
						fValue = parseFloat(item.latitude);
						f2Value = parseFloat(item.longitude);
		
						marker = new google.maps.Marker({
			                position: new google.maps.LatLng(fValue, f2Value),
			                map: map
			            });
			            flightPlanCoordinates.push(marker.getPosition());
			            bounds.extend(marker.position);
		
			            google.maps.event.addListener(marker, 'click', (function (marker, i) {
			                return function () {
			                    infowindow.setContent(item.date_time);
			                    infowindow.open(map, marker);
			                }
			            })(marker, i));
		
					});
		
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

	        map.fitBounds(bounds);
	        
	        // directions service
	        var start = flightPlanCoordinates[0];
	        var end = flightPlanCoordinates[flightPlanCoordinates.length - 1];
	        var waypts = [];
	        for (var i = 1; i < flightPlanCoordinates.length - 1; i++) {
	            waypts.push({
	                location: flightPlanCoordinates[i],
	                stopover: true
	            });
	        }
	        calcRoute(start, end, waypts);
	    }
	}

	function calcRoute(start, end, waypts) {
	    var request = {
	        origin: start,
	        destination: end,
	        waypoints: waypts,
	        optimizeWaypoints: true,
	        travelMode: google.maps.TravelMode.DRIVING
	    };
	    directionsService.route(request, function (response, status) {
	        if (status == google.maps.DirectionsStatus.OK) {
	            directionsDisplay.setDirections(response);
	            var route = response.routes[0];
	            var summaryPanel = document.getElementById('directions_panel');
	            summaryPanel.innerHTML = '';
	            // For each route, display summary information.
	            /*
	            for (var i = 0; i < route.legs.length; i++) {
	                var routeSegment = i + 1;
	                summaryPanel.innerHTML += '<b>Route Segment: ' + routeSegment + '</b><br>';
	                summaryPanel.innerHTML += route.legs[i].start_address + ' to ';
	                summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
	                summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
	            }
	            */
	        }
	    });
	}
	google.maps.event.addDomListener(window, 'load', initialize);
	</script>
	
	<script></script>
</html>
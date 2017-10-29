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
	<body onLoad="initMap()">
	
		<div id="map"></div>
		
		<!-- Skripta koja radi ajax poziv, inicijalizaciju mape, prikaz markera na mapi -->
		<script>
			function initMap() {
	
				var locations = [];
				var ctx = "${pageContext.request.servletContext.contextPath}"
	
				//Pravimo ajax poziv da dobavimo galerije i slike od korisnika
				$.ajax({
					dataType : "text",
					type : "GET",
					url : "" + ctx + "/gallery/get/locations",
					success : function(response) {
	
						//Imamo response, to znaci da je uspješno prošao ajax
						var jsonData = response;
	
						console.log("Response: " + response);
	
						//Rastavljamo po / string jer smo tako poslali iz kontrolera
						var locations_str = response.split('/');
	
						//Opcije za mapu koje postoje, ovdje se mapa "centrira", gdje će pokazivati kad se prikaže na pocetku
						var mapOptions = {
							zoom : 4,
							center : new google.maps.LatLng(45.0, 15.0)
						}
	
						//Pravimo instancu mape sa opcijama gore definisanim
						var map = new google.maps.Map(document
								.getElementById("map"), mapOptions);
	
						//Sad prolazimo kroz string koji smo rastavili po / i uzimamo Float vrijednost latitude i longitude
						for (var i = 0; i < locations_str.length; i++) {
	
							//Imamo Float vrijednost stringa
							fValue = parseFloat(locations_str[i]);
							f2Value = parseFloat(locations_str[i + 1]);
	
							//Pravimo objekat sa latitudom i longitudom
							var myLatlng = new google.maps.LatLng(fValue, f2Value);
	
							console.log("lat: " + fValue + ", long" + f2Value);
	
							//Pravimo novi marker da bi ga mogli prikazati na mapi
							var marker = new google.maps.Marker({
								position : myLatlng,
								title : "Slika!"
							});
	
							//Dodajemo marker na mapu
							marker.setMap(map);
	
							var push_to_array = {
								lat : fValue,
								lng : f2Value
							}
							locations.push(push_to_array);
	
							i++;
	
						}
						//Ako smo došli ovdje, znači da nam nije prošao ajax poziv, imamo error i ispišemo ga u prozoru
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
			}
		</script>

	</body>
</html>
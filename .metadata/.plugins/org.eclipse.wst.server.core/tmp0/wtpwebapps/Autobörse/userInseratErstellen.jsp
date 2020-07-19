<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" charset="ISO-8859-1">
	<title>Autobörse</title>
	<link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300&family=Roboto:wght@300&display=swap" rel="stylesheet">
	<link href="style.css" rel="stylesheet">
</head>
<body>

	<jsp:include page="navigationbar.jsp" />
	<div class="page">
		<jsp:include page="sidebarUserProfil.jsp" />
			<div class="user-page">
				  <div class="userform">
					<form method="post" action="InseratServlet">
						<h2>Autoverkaufen</h2>
						<input type="text" placeholder="Beschreibung" name="beschreibung"/>
						<select name="cars" id="cars">
						  <option value="vw">VW</option>
						  <option value="bmw">BMW</option>
						  <option value="mercedes">Mercedes</option>
						  <option value="audi">Audi</option>
						</select><br/>
						<input type="text" placeholder="Bezeichnung" name="bezeichnung"/>
						<label>Motorart:</label><br/>
						<a>Benzin</a> <input type="radio" name="motor" value="benzin"> 	
						<a>Diesel</a> <input type="radio" name="motor" value="diesel">	
						<a>Elektro</a> <input type="radio" name="motor" value="elektro">	
						<input type="text" placeholder="Leistung" name="leistung"> <br/>
						<label>Größe:</label><br/>
						<a>3-Türer</a> <input type="radio" name="groeße" value="fünf">		
						<a>5-Türer</a> <input type="radio" name="groeße" value="drei">		
						<input type="text" placeholder="KM-Stand" name="kmStand"> <br/>
						<input type="text" placeholder="Ausstattung" name="ausstattung"/> <br/>
						<button type="submit">Auto einstellen</button>
					</form>						
				</div>
			</div>
	</div>
	
</body>
</html>
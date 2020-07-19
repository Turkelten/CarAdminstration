<div class="searchsidebar-container">	
			<h2>Suchen</h2>
			<div class="searchsidebar">	    
		      	<form action="FilterServlet" method="get"><br/>
					<label for="cars">Marke:</label>
					<select name="cars" id="cars">
						  <option value="vw">VW</option>
						  <option value="bmw">BMW</option>
						  <option value="mercedes">Mercedes</option>
						  <option value="audi">Audi</option>
					</select><br/>
					<label>Bezeichnung:</label> <br/>
					<input type="text" name="bez"> <br/>
					<label>Motortyp:</label><br/><br/>
					<input type="radio" name="motor" value="benzin">Benzin<br/>
					<input type="radio" name="motor" value="diesel">Diesel<br/>
					<input type="radio" name="motor" value="elektro">Elektro<br/>
					<label>Leistung:</label>
					<input type="text" name="leistung"> <br/>
					<label>Größe:</label><br/>
					<input type="radio" name="groeße" value="fünf">3-Türer<br/>
					<input type="radio" name="groeße" value="drei">5-Türer<br/>
				    <label>Max KM-Stand:</label>
					<input type="text" name="kmStand"> <br/>
					<br/><button type="submit">Suchen</button> <button type="reset">Reset</button>
				</form>
			</div>
		</div>
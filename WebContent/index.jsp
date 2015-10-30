<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Perrera Mobile</title>
	
	<link rel="stylesheet" href="js/jqm/jquery.mobile-1.4.5.min.css">	    
	<script src="js/jqm/jquery.js"></script>	
	<script src="js/jqm/jquery.mobile-1.4.5.min.js"></script>
	
</head>
<body>
	<div data-role="page" id="home">
	
		<div data-role="header">
			<h2>Cabecera</h2>
		</div><!-- /header -->
	
		<div data-role="main">
			<ul data-role="listview" data-inset="true" data-filter="true">
				<li><a href="#modificar">Buba</a></li>
				<li><a href="#modificar">Canela</a></li>
				<li><a href="#modificar">Ribgo</a></li>
				<li><a href="#modificar">Sarpu</a></li>
				<li><a href="#modificar">LLIdo</a></li>
				<li><a href="#modificar">Puklgas</a></li>
				<li><a href="#modificar">Rabioso</a></li>
			</ul>
		</div>
		
		<div data-role="footer">
			Pie de pagina
		</div>
		
	</div><!-- /page HOME -->
	
	
	<div data-role="page" id="modificar">
	
		<div data-role="header">
			<a href="#home">Home</a>
			<h2>Modificar-DEtalle</h2>
		</div><!-- /header -->
	
		<div data-role="main">
			
			
			<label for="nombre">Nombre:</label>
			<input type="text" name="nombre" id="nombre" value="">
			
			<label for="raza">Raza:</label>
			<input type="text" name="raza" id="raza" value="">
			
			
			<a href="#" id ="botonModificar" 
			   data-role="button"
			    data-theme="e" 
			   >
				Modificar
			</a> 
		</div>
		
		
		
	</div><!-- /page modificar -->
	

</body>
</html>

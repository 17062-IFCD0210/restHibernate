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

	<!-- HOME -->
	<div data-role="page" id="home">
	
		<div data-role="header">
			<h2>Perrera</h2>
		</div><!-- /header -->
	
		<div data-role="navbar">
			<ul id="perros_opciones">
				<li><a href="#crear" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-plus">Crear nuevo perro</a></li>
			</ul>
		</div>
		<div data-role="main">
			<ul id="perros_home" data-role="listview" data-inset="true" data-filter="true">
			</ul>
		</div>
		
		<div data-role="footer">
			Pie de pagina
		</div>
		
	</div><!-- /page HOME -->
	
	<!-- MODIFICAR -->
	<div data-role="page" id="modificar">
	
		<div data-role="header">
			<a href="#home" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-home">Home</a>
			<h2>Modificar-Detalle</h2>
		</div><!-- /header -->
	
		<div data-role="main">
			<input type="hidden" name="id_modificar" id="id_modificar">
			
			<label for="nombre_modificar">Nombre:</label>
			<input type="text" name="nombre_modificar" id="nombre_modificar" value="">
			
			<label for="rabo_modificar">Rabo:</label>
			<input type="text" name="rabo_modificar" id="rabo_modificar" value="">
			
			<div data-role="navbar">
				<ul>
					<li><a href="#" id ="botonModificar" data-role="button" data-theme="e"
						class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-edit">Modificar</a>
					</li>
					<li><a href="#eliminar" id ="botonEliminar"
						class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-delete">Eliminar</a>
					</li>
				</ul>
			</div>
		</div>
	</div><!-- /page modificar -->
	
	<!-- CREAR -->
	<div data-role="page" id="crear">
		<div data-role="header">
			<a href="#home" class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-home">Home</a>
			<h2>Crear Perro</h2>
		</div><!-- /header -->
	
		<div data-role="main">
			<label for="nombre_crear">Nombre:</label>
			<input type="text" name="nombre_crear" id="nombre_crear">
			
			<label for="rabo_crear">Rabo:</label>
			<input type="text" name="rabo_crear" id="rabo_crear">
			
			<a href="#" id ="botonCrear" 
			   data-role="button"
			    data-theme="e" 
			    class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-plus">
				Crear
			</a> 
			
		</div>
	</div><!-- /page crear -->

<script>

	/* Antes de cargar la Home llamar al Servicio y rellenar lista */
	
	var servicio = "//localhost:8080/restHibernate/service/"; //coje el protocolo por el que acceda automaticamente http o https
	var perros = []; //Coleccion de Perros
	var perro_selec; //perro seleccionado
	
	//Cuando se inicializa la pagina se carga. Solo se inicia una vez
	//$(document).on("pageinit", "#home", function() {
	
	// Que cargue la tabla cada vez que entre esta pagina por si se ha creado o eliminado algun elemento algo
	$(document).on("pagebeforeshow", "#home", function() {
		console.debug('"pagebeforeshow": Home cargada');
		
		//Cojemos los datos mediante Web Service REST - GET 
		$.get(servicio + "perro", function(data){ //o $.ajax o $.getJSON
			var output = '';
			perros = [];
			$.each(data, function(index, value){ //hacemos un for de todos los datos recogidos
				output += '<li><a href="#" data-id="' + value.id + '">' + value.nombre + '</a></li>';
				perros.push(value); //Inserto dentro del array cada objeto/Perro
			});
			$('#perros_home').html(output).listview("refresh"); //llamar un evento para que refreque esa listview
		});
	});
	
	/* Detectar click en listado perros HOME */
	$('#perros_home').on('vclick', 'li', function(event) {
		//perro_selec = perros.indexOf( $(this).id, $(this).data('id')); //Devuelve el perro si encuentra el valor que le hemos dado (el id de <li data-id></li>)
		perro_selec = perros[$(this).index()]; //Segun la posicion del <li>, podria ser 'li a' como en css
        $.mobile.navigate("#modificar"); //Ir a la pagina modificar
    });
	
	$('#perros_opciones').on('vclick', 'li', function(event) {
        $.mobile.navigate("#crear"); //Ir a la pagina crear
    });
	
	
	//Ejecuta antes de que se muestre la pagina
	$(document).on("pagebeforeshow", "#modificar", function() {
		//Rellenar formulario con "perro_seleccionado"
		$("#id_modificar").val(perro_selec.id); //introducimos valor del campo
		$("#nombre_modificar").val(perro_selec.nombre);
		$("#rabo_modificar").val(perro_selec.rabo);
	});
	
	/* PUT: Detectar click en boton MODIFICAR */
	$('#modificar').on('vclick', 'a[id=botonModificar]', function(event) {
		$.ajax({
			  method: "PUT",
			  url: servicio + "perro/" + $("#id_modificar").val() + "/" + $("#nombre_modificar").val() + "/" + $("#rabo_modificar").val()
			})
			  .done(function( msg ) {
			    alert( "Datos actualizados: " + msg );
		});
		$.mobile.navigate("#home"); //Ir a la pagina HOME
    });
	
	$(document).on("pagebeforeshow", "#crear", function() {
		//Vaciar formulario
		$("#nombre_crear").val("");
		$("#rabo_crear").val("");
	});
	
	/* POST: Detectar click en boton CREAR */
	$('#crear').on('vclick', 'a', function(event) {
		
		$.ajax({
			method: "POST",
			url: servicio + "perro/" + $("#nombre_crear").val() + "/" + $("#rabo_crear").val()
		})
			.done(function( msg ) {
				alert( "Datos salvados: " + msg );
			});
		$.mobile.navigate("#home"); //Ir a la pagina HOME
	});
	
	/* DELETE: Detectar click de cada boton en la lista*/
	$('#modificar').on('vclick', 'a[id=botonEliminar]', function(event) {
		
		perro_selec = perros[$(this).index()]; //Segun la posicion del input 'li input'
		
		$.ajax({
			method: "DELETE",
			url: servicio + "perro/" + $("#id_modificar").val()
		})
			.done(function( msg ) {
				alert( "Datos salvados: " + msg );
			});
		$.mobile.navigate("#home"); //Ir a la pagina HOME
	});
	
</script>
	
</body>
</html>

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
			<h2>Perrera</h2>
		</div>
		<!-- /header -->

		<div data-role="navbar">
			<ul>
				<li><a href="#crear"
					class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-plus">Crear
						nuevo perro</a></li>
				<li><a href="#eliminar"
					class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-delete">Eliminar
						perro</a></li>
			</ul>
		</div>
		<div data-role="main">
			<ul id="perros_home" data-role="listview" data-inset="true" data-filter="true">
				<li>Sin cargar perros</li>
			</ul>
		</div>

		<div data-role="footer">Pie de pagina</div>

	</div>
	<!-- /page HOME -->


	<div data-role="page" id="modificar">

		<div data-role="header">
			<a href="#home"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-home">Home</a>
			<h2>Modificar-Detalle</h2>
		</div>
		<!-- /header -->

		<div data-role="main">


			<label for="nombre">Nombre:</label> <input type="text" name="nombre"
				id="nombre" value=""> <label for="raza">Raza:</label> <input
				type="text" name="raza" id="raza" value=""> <a href="#"
				id="botonModificar" data-role="button" data-theme="e"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-edit">
				Modificar </a>
		</div>



	</div>
	<!-- /page modificar -->

	<div data-role="page" id="crear">

		<div data-role="header">
			<a href="#home"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-home">Home</a>
			<h2>Crear Perro</h2>
		</div>
		<!-- /header -->

		<div data-role="main">


			<label for="nombre">Nombre:</label> <input type="text" name="nombre"
				id="nombre"> <label for="raza">Raza:</label> <input
				type="text" name="raza" id="raza"> <a href="#"
				id="botonCrear" data-role="button" data-theme="e"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-plus">
				> Crear </a>
		</div>



	</div>
	<!-- /page crear -->

	<div data-role="page" id="eliminar">

		<div data-role="header">
			<a href="#home"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-home">Home</a>
			<h2>Eliminar Perro</h2>
		</div>
		<!-- /header -->

		<div data-role="main">

			<div class="ui-field-contain">
				<label for="select-perro">Perro a eliminar:</label> <select
					name="select-perro" id="select-perro">
					<option value="1">Buba</option>
					<option value="2">Canela</option>
					<option value="3">Ribgo</option>
					<option value="4">Sarpu</option>
					<option value="5">LLIdo</option>
					<option value="6">Puklgas</option>
					<option value="6">Rabioso</option>
				</select>

			</div>


			<a href="#" id="botonEliminar" data-role="button" data-theme="e"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-delete">
				Eliminar </a>
		</div>

	</div>
	<!-- /page crear -->


	<script>
		/* Antes de carga la HOME, llamar al servicio y rellenar lista */
		var servicio = "//localhost:8080/restHibernate/service/";

		$(document).on("pageinit", "#home", function() {
			console.debug('"pageinit": Home cargada');

			$.get(servicio + "perro", function(data) {
				var output = '';
				$.each(data, function(index, value) {
					output += '<li><a href="#">' + value.nombre + '</a></li>';
				});
				$('#perros_home').html(output).listview("refresh");
			});

		});
	</script>


</body>
</html>

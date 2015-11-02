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
			<h2>Perrera USUARIO</h2>
		</div>
		<!-- /header -->

		<div data-role="navbar">
			<ul>
				<li><a href="#crear"
					class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-plus">
						CREAR PERRO</a></li>

			</ul>
		</div>
		<div data-role="main">
			<ul id="perros_home" data-role="listview" data-insert="true"
				data-filter="true">


			</ul>
			<div data-role="contarPerrera"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-plus"
				id="contarPerrera"></div>


		</div>


		<div data-role="footer"
			class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-plus"
			id="id_footer">FIN PAGINA</div>

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

			<input type="hidden" name="id_modificar" id="id_modificar"> <label
				for="nombre_modificar">Nombre:</label> <input type="text"
				name="nombre_modificar" id="nombre_modificar" value=""> <label
				for="raza_modificar">Raza:</label> <input type="text"
				name="raza_modificar" id="raza_modificar" value="">

			<div data-role="navbar">
				<ul>
					<li><a href="#" id="botonModificar" data-role="button"
						data-theme="e"
						class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-edit">
							Modificar </a></li>
					<li><a href="#" id="botonElimmar" data-role="button"
						data-theme="e"
						class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-edit">
							Eliminar</a></li>
				</ul>
			</div>
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


			<label for="nombre_crear">Nombre:</label> <input type="text"
				name="nombre_crear" id="nombre_crear"> <label
				for="raza_crear">Raza:</label> <input type="text" name="raza_crear"
				id="raza_crear"> <a href="#" id="botonCrear"
				data-role="button" data-theme="e"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-plus">
				Crear </a>
		</div>



	</div>
	<!-- /page crear -->

	<div data-role="page" id="eliminar">

		<div data-role="header">
			<input type="hidden" name="id_eliminar" id="id_eliminar"> <a
				href="#home"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-left ui-icon-home">Home</a>
			<h2>Eliminar Perro</h2>
		</div>
		<!-- /header -->

		<div data-role="main">

			<div class="ui-field-contain">
				<label for="select-perro">Perro a eliminar:</label> <select
					name="select-perro" id="select-perro">


				</select>

			</div>


			<a href="#" id="botonEliminar" data-role="button" data-theme="e"
				class="ui-btn ui-shadow ui-corner-all ui-btn-icon-bottom ui-icon-delete">
				Eliminar </a>
		</div>



	</div>
	<!-- /page crear -->


	<script>
		/* Antes de cargar la Home, llamar al servicio y rellenar lista*/
		var servicio = "//localhost:8080/restHibernate/service/";
		var perros = []; //coleccion de Perros
		var perro_selec;//saber que perro esta seleccionado
		$(document).on(
				"pagebeforeshow",
				"#home",
				function() {
					perros = [];
					console.debug('"pageinit" : Home cargada');

					$.get(servicio + "perro", function(data) {
						var output = '';
						$.each(data, function(index, value) {
							output += '<li><a href="#">' + value.nombre
									+ '</a></li>';
							perros.push(value);//metemos el perro  a la coleccion
						});
						$('#perros_home').html(output).listview("refresh");
						$('#contarPerrera').html(
								'TOTAL DE PERROS EN LA PERRERA:  '
										+ perros.length);

					});

				})//End pageINIT

		/*Detectar click en listado perros HOME*/
		$('#perros_home').on('vclick', 'li', function(event) {
			perro_selec = perros[$(this).index()];
			$.mobile.navigate("#modificar");
		});
		$(document).on("pagebeforeshow", "#modificar", function() {
			//rellenar formulario con "perro_seleccionado"
			$("#id_modificar").val(perro_selec.id);
			$("#nombre_modificar").val(perro_selec.nombre);
			$("#raza_modificar").val(perro_selec.raza);

		})

		//modificar
		$(document).on(
				'click',
				'#botonModificar',
				function() {

					$.ajax({
						type : 'PUT',
						url : servicio + "perro" + '/'
								+ $("#id_modificar").val() + '/'
								+ $("#nombre_modificar").val() + '/'
								+ $("#raza_modificar").val(),
						success : $.mobile.navigate("#home")

					});

				})

		//crear
		$(document).on(
				'click',
				'#botonCrear',
				function() {
					$.ajax({
						type : 'POST',
						url : servicio + "perro" + '/'
								+ $("#nombre_crear").val() + '/'
								+ $("#raza_crear").val(),
						success : $.mobile.navigate("#home")
					});

				})

		//eliminar 
		$(document).on('click', '#botonElimmar', function() {
			if (confirm('PERRO   ' + $("#id_modificar").val() + '  ELIMINAR')) {
				$.ajax({
					type : 'DELETE',
					url : servicio + "perro" + '/' + $("#id_modificar").val(),
					success : $.mobile.navigate("#home")
				});
			}

		})

		//total de perros
		//como contamos la perrera
	</script>

</body>
</html>

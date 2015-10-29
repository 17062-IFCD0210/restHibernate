package com.ipartek.formacion.ejemplos.RestMyKong.controller;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ipartek.formacion.ejemplos.RestMyKong.pojo.Perro;

@Path("/perro")
public class PerroController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {

		// ModeloGrado mg = new ModeloGrado();
		ArrayList<Perro> perros = new ArrayList<Perro>();
		perros.add(new Perro("Buba", "boxer"));
		perros.add(new Perro("Alice", "dalmata"));

		// mg.getAll(null);
		return Response.status(200).entity(perros).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByID(@PathParam("id") int idPerro) {

		try {
			// Simular Error interno Servidor
			if (idPerro <= 0) {
				throw new Exception("Error Interno 500");
			}

			// TODO consulta en BBDD
			// Simular que el idPerro == 13 no existe
			if (idPerro != 13) {
				Perro perro = new Perro("Soy el perro" + idPerro, "La mia");
				return Response.status(200).entity(perro).build();
			} else {
				return Response.status(204).build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}

	}

}

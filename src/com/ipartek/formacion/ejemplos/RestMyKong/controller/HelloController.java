package com.ipartek.formacion.ejemplos.RestMyKong.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ipartek.formacion.ejemplos.RestMyKong.pojo.Perro;

@Path("/hello")
public class HelloController {

	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;
		return Response.status(200).entity(output).build();

	}

	@GET
	@Path("/json/perro")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerro(@PathParam("param") String msg) {

		Perro perro = new Perro("Bubba", "Boxer");
		return Response.status(200).entity(perro).build();

	}

}

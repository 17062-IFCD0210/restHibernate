package com.ipartek.formacion.ejemplos.restHibernate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.ipartek.formacion.ejemplos.restHibernate.model.HibernateUtil;
import com.ipartek.formacion.ejemplos.restHibernate.pojo.FechaHora;
import com.ipartek.formacion.ejemplos.restHibernate.pojo.Perro;

/**
 * El poryecto hace refencia al proyecto skalada
 *
 * @author Curso
 *
 */
@Path("/perro")
@Api(value = "/perro")
public class PerroController {
	private Session s;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de todos los perros", notes = "Listado de perros existente en la perrera", response = Perro.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	public Response getAll() {
		try {
			this.s = HibernateUtil.getSession();
			this.s.beginTransaction();
			ArrayList<Perro> perros = (ArrayList<Perro>) this.s.createCriteria(
					Perro.class).list();
			this.s.beginTransaction().commit();
			this.s.close();

			return Response.ok().entity(perros).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Buscar un perro por su ID", notes = "Devuelve un perro por su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 404, message = "ID inexistente"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Perro perro = null;
			this.s = HibernateUtil.getSession();
			this.s.beginTransaction();
			perro = (Perro) this.s.get(Perro.class, idPerro);
			this.s.beginTransaction().commit();
			this.s.close();
			if (perro == null) {
				return Response.noContent().build();
			}
			return Response.ok().entity(perro).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro por su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Perro eliminado"),
			@ApiResponse(code = 404, message = "ID inexistente"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	public Response delete(@PathParam("id") int idPerro) {

		try {
			Perro pElimnar = null;
			this.s = HibernateUtil.getSession();
			this.s.beginTransaction();
			pElimnar = (Perro) this.s.get(Perro.class, idPerro);
			this.s.beginTransaction().commit();
			this.s.close();
			if (pElimnar == null) {
				return Response.status(404).build();
			} else {
				this.s = HibernateUtil.getSession();
				this.s.beginTransaction();
				this.s.delete(pElimnar);
				this.s.beginTransaction().commit();
				this.s.close();
				return Response.status(200).entity(new FechaHora()).build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Todo OK"),
			@ApiResponse(code = 204, message = "Campos vacios"),
			@ApiResponse(code = 409, message = "Perro existente"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	public Response post(@PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			if (nombrePerro.equalsIgnoreCase("") && razaPerro.equalsIgnoreCase("")) {
				return Response.status(204).build();
			}
			Perro pCreado = new Perro(nombrePerro, razaPerro);
			int idpCreado = 0;
			this.s = HibernateUtil.getSession();
			this.s.beginTransaction();
			idpCreado = (int) this.s.save(pCreado);
			this.s.beginTransaction().commit();
			this.s.close();
			if (idpCreado != 0) {
				return Response.status(201).entity(pCreado).build();
			} else {
				return Response.status(409).build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "Perro inexistente"),
			@ApiResponse(code = 404, message = "ID inexistente"),
			@ApiResponse(code = 409, message = "Perro existente"),
			@ApiResponse(code = 500, message = "Error en el servidor") })
	public Response put(@PathParam("id") int idPerro,
			@PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			Perro pModificar = null;
			if (nombrePerro.equalsIgnoreCase("") && razaPerro.equalsIgnoreCase("")) {
				return Response.status(204).build();
			}
			this.s = HibernateUtil.getSession();
			this.s.beginTransaction();
			pModificar = (Perro) this.s.get(Perro.class, idPerro);
			this.s.beginTransaction().commit();
			this.s.close();
			if (pModificar == null) {
				return Response.status(404).build();
			} else {
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);
				this.s = HibernateUtil.getSession();
				this.s.beginTransaction();
				this.s.update(pModificar);
				this.s.beginTransaction().commit();
				this.s.close();
				return Response.status(200).entity(pModificar).build();
			}
		} catch (Exception e) {
			return Response.status(500).build();

		}
	}
}

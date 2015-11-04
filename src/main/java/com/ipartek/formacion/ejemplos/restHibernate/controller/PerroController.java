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
	@ApiOperation(
			value = "Listado de perros",
			notes = "Listado de perros existentes en la perrera, limitado a 1.000",
			response = Perro.class,
			// respuesta de tipo Perro
			responseContainer = "List")
	// El contenedor es una lista
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor")})
	public Response getAll() {
		try {
			s = HibernateUtil.getSession();
			s.beginTransaction();
			ArrayList<Perro> perros = (ArrayList<Perro>) s.createCriteria(Perro.class).list();
			s.beginTransaction().commit();
			s.close();

			return Response.ok().entity(perros).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID",
	notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 404, message = "No existe Perro con esa ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor")})
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Perro perro = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();
			perro = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if (perro == null) {
				// Error 204. Sin contenido para ese id. No lo encuentra
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
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Perro eliminado"),
			@ApiResponse(code = 404, message = "No existe perro con ese ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor")})
	public Response delete(@PathParam("id") int idPerro) {

		try {
			Perro pElimnar = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if (pElimnar == null) {
				// Error 204. Sin contenido para ese id. No lo encuentra
				return Response.noContent().build();
			} else {
				s = HibernateUtil.getSession();
				s.beginTransaction();
				s.delete(pElimnar);
				s.beginTransaction().commit();
				s.close();
				// Tiene que devolver un objeto json. Se ha creado un pojo(bean) para que devuelva algo
				// Response 200 = Ok
				return Response.ok().entity(new FechaHora()).build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = {
			// En POST se pone el codigo 201 = 200
			@ApiResponse(code = 201, message = "Perro creado con exito"),
			// Si hayConflictos en la bbdd
			@ApiResponse(code = 409, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor")})
	public Response post(@PathParam("nombre") String nombrePerro, @PathParam("raza") String razaPerro) {
		try {
			Perro pCreado = new Perro(nombrePerro, razaPerro);
			int idpCreado = 0;
			s = HibernateUtil.getSession();
			s.beginTransaction();
			idpCreado = (int) s.save(pCreado);
			s.beginTransaction().commit();
			s.close();
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
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Modificado correctamente"),
			@ApiResponse(code = 204, message = "No existe perro con ese ID"),
			@ApiResponse(code = 409, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor")})
	public Response put(@PathParam("id") int idPerro, @PathParam("nombre") String nombrePerro, @PathParam("raza") String razaPerro) {
		try {
			Perro pModificar = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();
			pModificar = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if (pModificar == null) {
				return Response.noContent().build();
			} else {
				//Mete params
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);
				s = HibernateUtil.getSession();
				s.beginTransaction();
				//Se tendría que haber creado el modelo para que cierre y abra las conexiones y mapee los params
				s.update(pModificar);
				s.beginTransaction().commit();
				s.close();
				return Response.status(200).entity(pModificar).build();
			}
		} catch (Exception e) {
			//Si casca 500
			return Response.status(500).build();

		}
	}
}

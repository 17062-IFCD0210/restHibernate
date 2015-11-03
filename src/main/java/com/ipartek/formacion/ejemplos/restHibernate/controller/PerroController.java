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
@Api(value = "/perro"  )
public class PerroController {
	private Session s;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
		value = "Listado de Perros",
	    notes = "Listado de perros existentes en la perrera limitado a 1.000",
	    response = Perro.class,
	    responseContainer = "List")	
	@ApiResponses(
		value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor")
		})
	public Response getAll() {		
		try{
			s = HibernateUtil.getSession();
			s.beginTransaction();
			ArrayList<Perro> perros = (ArrayList<Perro>)s.createCriteria(Perro.class).list();
			s.beginTransaction().commit();
			s.close();		
	
			return Response.ok().entity(perros).build();
		} catch (Exception e){
			return Response.serverError().build();
		}
	}

	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID",
	    notes = "devuelve un perro mediante el paso de su ID",
	    response = Perro.class,
	    responseContainer = "Perro")	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor")
	})
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Perro perro = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();
			perro = (Perro) s.get(Perro.class, idPerro);			
			s.beginTransaction().commit();
			s.close();
			if(perro == null){
				return Response.noContent().build(); //status(204).build();
			}
			return Response.ok().entity(perro).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
		value = "Elimina un perro por su ID",
	    notes = "Elimina un perro mediante el paso de su ID",
	    response = Perro.class,
	    responseContainer = "FechaHora")	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Perro eliminado"),
			@ApiResponse(code = 204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor")
	})
	public Response delete(@PathParam("id") int idPerro) {

		try {
			Perro pEliminar = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();			
			pEliminar = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if(pEliminar == null){
				return Response.noContent().build();//status(204).build();
			} else {
				s = HibernateUtil.getSession();
				s.beginTransaction();	
				s.delete(pEliminar);
				s.beginTransaction().commit();
				s.close();
				return Response.ok().entity(new FechaHora()).build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
		value = "Añade un perro",
	    notes = "Crea y persiste un nuevo perro",
	    response = Perro.class,
	    responseContainer = "Perro")	
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Perro Creado con éxito"),
			@ApiResponse(code = 409, message = "Perro ya existente"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor")
	})
	public Response post(@PathParam("nombre") String nombrePerro,
						 @PathParam("raza") String razaPerro) {
		try {

			Perro pCreado = new Perro(nombrePerro, razaPerro);
			int idpCreado = 0;
			s = HibernateUtil.getSession();
			s.beginTransaction();	
			idpCreado = (int) s.save(pCreado);
			s.beginTransaction().commit();
			s.close();
			if (idpCreado != 0){
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
	@ApiOperation(
		value = "Modifica un perro",
		notes = "Modifica un perro ya existente mediante su identificador",
	    response = Perro.class,
	    responseContainer = "Perro")	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = 409, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor")
	})
	public Response put(@PathParam("id") int idPerro,
			@PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			Perro pModificar = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();			
			pModificar = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if(pModificar == null ){
				return Response.noContent().build();
			} else {
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);
				s = HibernateUtil.getSession();
				s.beginTransaction();	
				s.update(pModificar);
				s.beginTransaction().commit();
				s.close();
				return Response.ok().entity(pModificar).build();
			}
		} catch (Exception e) {
			return Response.status(500).build();

		}
	}
}

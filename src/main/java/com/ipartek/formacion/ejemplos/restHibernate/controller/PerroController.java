package com.ipartek.formacion.ejemplos.restHibernate.controller;

import java.util.ArrayList;
import java.util.Date;

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
public class PerroController {
	Date date = new Date();
	Perro perro = new Perro();

	/**
	 * Devuelve todos los perros de skalada
	 *
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {		
		try{
			Session s = HibernateUtil.getSession();
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
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Perro pPerro = null;
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			pPerro = (Perro) s.get(Perro.class, idPerro);			
			s.beginTransaction().commit();
			s.close();
			if(pPerro == null){
				return Response.status(404).entity(new FechaHora()).build();
			}
			return Response.ok().entity(pPerro).build();

		} catch (Exception e) {
			return Response.serverError().build();

		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int idPerro) {

		try {
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			// simular error interno del servidor error 500
			if (idPerro <= 0) {
				throw new Exception("Error Interno 500");
			}
			Perro pElimnar = (Perro) s.get(Perro.class, idPerro);
			s.delete(pElimnar);
			s.beginTransaction().commit();
			s.close();
			return Response.status(200).entity(new FechaHora()).build();

		} catch (Exception e) {
			return Response.serverError().build();

		}
	}

	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(@PathParam("nombre") String nombrePerro,
						 @PathParam("raza") String razaPerro) {
		try {
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			Perro pCreado = new Perro(nombrePerro, razaPerro);
			s.save(pCreado);
			s.beginTransaction().commit();
			s.close();

			return Response.status(201).entity(pCreado).build();

		} catch (Exception e) {
			return Response.serverError().build();

		}
	}

	@PUT
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response put(@PathParam("id") int idPerro,
			@PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {

		try {
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			// simular error interno del servidor error 500
			if (idPerro < 0) {
				throw new Exception("Error Interno 500");
			}
			Perro pModificar = (Perro) s.get(Perro.class, idPerro);
			pModificar.setNombre(nombrePerro);
			pModificar.setRaza(razaPerro);
			s.update(pModificar);
			s.beginTransaction().commit();
			s.close();

			return Response.status(200).entity(pModificar).build();

		} catch (Exception e) {
			return Response.status(500).build();

		}
	}
}

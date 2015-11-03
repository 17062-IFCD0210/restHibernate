package com.ipartek.formacion.ejemplos.restHibernate.controller;

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
public class PerroController {
	private Session s;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
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
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Perro perro = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();
			perro = (Perro) s.get(Perro.class, idPerro);			
			s.beginTransaction().commit();
			s.close();
			if(perro == null){
				return Response.status(404).build();
			}
			return Response.ok().entity(perro).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int idPerro) {

		try {
			Perro pElimnar = null;
			s = HibernateUtil.getSession();
			s.beginTransaction();			
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if(pElimnar == null){
				return Response.status(404).build();
			} else {
				s = HibernateUtil.getSession();
				s.beginTransaction();	
				s.delete(pElimnar);
				s.beginTransaction().commit();
				s.close();
				return Response.status(200).entity(new FechaHora()).build();
			}
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
			if(nombrePerro.equalsIgnoreCase("") && razaPerro.equalsIgnoreCase("")){
				return Response.status(204).build();
			}			
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
				return Response.status(404).build();
			}

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
			Perro pModificar = null;
			if(nombrePerro.equalsIgnoreCase("") && razaPerro.equalsIgnoreCase("")){
				return Response.status(204).build();
			}			
			s = HibernateUtil.getSession();
			s.beginTransaction();			
			pModificar = (Perro) s.get(Perro.class, idPerro);
			s.beginTransaction().commit();
			s.close();
			if(pModificar == null ){
				return Response.status(404).build();
			} else {
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);
				s = HibernateUtil.getSession();
				s.beginTransaction();	
				s.update(pModificar);
				s.beginTransaction().commit();
				s.close();
				return Response.status(200).entity(pModificar).build();
			}

		} catch (Exception e) {
			return Response.status(500).build();

		}
	}
}

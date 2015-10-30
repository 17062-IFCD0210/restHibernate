package com.ipartek.formacion.ejemplos.RestMyKong.controller;

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

import com.ipartek.formacion.ejemplos.RestMyKong.model.HibernateUtil;
import com.ipartek.formacion.ejemplos.RestMyKong.pojo.Perro;

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
		ArrayList<Perro> perros = new ArrayList<Perro>();
		// ModeloGrado mg = new ModeloGrado();
		Session s = HibernateUtil.getSession();
		perros = (ArrayList<Perro>) s.createQuery("from Perro").list();

		System.out.println("GETALL:" + "Numero de perros en la perrera: "
				+ perros.size());
		for (int i = 0; i < perros.size(); i++) {
			System.out.println("Perro: " + i);
			perros.get(i).toString();
		}
		s.close();

		return Response.status(200).entity(perros).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			// simular error interno del servidor error 500
			if (idPerro <= 0) {
				throw new Exception("Error Interno 500");
			}

			Perro pPerro = (Perro) s.get(Perro.class, idPerro);

			System.out.println("nombre: " + pPerro.getNombre() + "rabo: "
					+ pPerro.getRabo());
			s.beginTransaction().commit();
			s.close();
			return Response.status(200).entity(pPerro).build();

		} catch (Exception e) {
			return Response.status(500).build();

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
			return Response.status(200)
					.entity("eliminado: " + idPerro + " " + this.date).build();

		} catch (Exception e) {
			return Response.status(500).build();

		}
	}

	@POST
	@Path("/{nombre}/{rabo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(@PathParam("nombre") String nombrePerro,
			@PathParam("rabo") String raboPerro) {

		try {
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			Perro p7 = new Perro(nombrePerro, raboPerro);
			s.save(p7);
			s.beginTransaction().commit();
			s.close();

			return Response
					.status(200)
					.entity("creado:" + "nombre:" + nombrePerro + "rabo: "
							+ raboPerro + this.date).build();

		} catch (Exception e) {
			return Response.status(500).build();

		}
	}

	@PUT
	@Path("/{id}/{nombre}/{rabo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response put(@PathParam("id") int idPerro,
			@PathParam("nombre") String nombrePerro,
			@PathParam("rabo") String raboPerro) {

		try {
			Session s = HibernateUtil.getSession();
			s.beginTransaction();
			// simular error interno del servidor error 500
			if (idPerro < 0) {
				throw new Exception("Error Interno 500");
			}
			Perro pModificar = (Perro) s.get(Perro.class, idPerro);
			pModificar.setNombre(nombrePerro);
			pModificar.setRabo(raboPerro);
			s.update(pModificar);
			s.beginTransaction().commit();
			s.close();

			return Response.status(200).entity(pModificar).build();

		} catch (Exception e) {
			return Response.status(500).build();

		}
	}
}

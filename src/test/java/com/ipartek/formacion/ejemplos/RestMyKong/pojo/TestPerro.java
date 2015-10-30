package com.ipartek.formacion.ejemplos.RestMyKong.pojo;

import java.util.ArrayList;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.ejemplos.RestMyKong.model.HibernateUtil;

public class TestPerro {
	Perro p = new Perro();
	Session s;

	@BeforeClass
	public static void llenarPerrera() throws Exception {

		Session s = HibernateUtil.getSession();
		s.beginTransaction();
		Perro p1 = new Perro("Lagun1", "uno");
		s.save(p1);
		Perro p2 = new Perro("Lagun2", "dos");
		s.save(p2);
		Perro p3 = new Perro("Lagun3", "tres");
		s.save(p3);
		Perro p4 = new Perro("Lagun4", "cuatro");
		s.save(p4);
		Perro p5 = new Perro("Lagun5", "cinco");
		s.save(p5);
		Perro p6 = new Perro("Lagun6", "seis");
		s.save(p6);
		new Perro("Lagun6", "seis");
		s.beginTransaction().commit();
		s.close();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		this.s = HibernateUtil.getSession();
		this.s.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		this.s.beginTransaction().commit();
		this.s.close();
	}

	/**
	 * Con la perrepa llena hasta 6, mete el perro 7
	 */
	@Test
	public void testCrearPerros() {

		Perro p7 = new Perro("Lagun7", "siete");
		this.s.save(p7);

	}

	@Test
	public void testCrearPerrosAll() {

		ArrayList<Perro> perros = new ArrayList<Perro>();
		Session s = HibernateUtil.getSession();
		perros = (ArrayList<Perro>) s.createQuery("from Perro").list();
		System.out.println("GETALL:" + "Numero de perros en la perrera: "
				+ perros.size());
		for (int i = 0; i < perros.size(); i++) {
			System.out.println("Perro: " + i);
			perros.get(i).toString();
		}
		s.close();
	}

	@Test
	public void testEliminarPerro() {

		Perro pElimnar = (Perro) this.s.get(Perro.class, 2);
		this.s.delete(pElimnar);

	}

	@Test
	public void testModificarPerro() {

		Perro pModificar = (Perro) this.s.get(Perro.class, 4);
		pModificar.setNombre("Lagun Modificado");
		pModificar.setRabo("Rabo Modificado");
		this.s.update(pModificar);

	}

	@Test
	public void testObtenerPerro() {

		Perro pPerro = (Perro) this.s.get(Perro.class, 4);

		System.out.println("nombre: " + pPerro.getNombre() + "rabo: "
				+ pPerro.getRabo());

	}
}

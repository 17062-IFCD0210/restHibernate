package com.ipartek.formacion.ejemplos.restHibernate.pojo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.ejemplos.restHibernate.controller.PerroController;

public class TestPerroController {

	PerroController p = new PerroController();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PerroController p1 = new PerroController();
		p1.post("Perro1", "Raza1");
		p1.post("Perro2", "Raza2");
		p1.post("Perro3", "Raza3");
		p1.post("Perro4", "Raza4");
		p1.post("Perro5", "Raza5");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PerroController p1 = new PerroController();
		p1.delete(1);
		p1.delete(2);
		p1.delete(3);
		p1.delete(4);
		p1.delete(5);
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrearPerro() {
		assertEquals(this.p.post("Raza", "Racero").getStatus(), 201);

	}

	@Test
	public void testGetAllPerros() {
		assertEquals(this.p.getAll().getStatus(), 200);
	}

	@Test
	public void testGetPerro() {
		assertEquals(this.p.getById(1).getStatus(), 200);
		assertEquals(this.p.getById(10).getStatus(), 204);
	}

	@Test
	public void testUploadPerro() {
		assertEquals(this.p.put(1, "Toby", "Boxer").getStatus(), 200);
	}

}

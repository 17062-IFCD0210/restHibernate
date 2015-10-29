package com.ipartek.formacion.ejemplos.RestMyKong.pojo;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.ejemplos.RestMyKong.model.HibernateUtil;

public class PerroTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreatePerro() {

		Session s = HibernateUtil.getSession();
		s.beginTransaction();

		Perro p = new Perro("Bubba", "boxer");

		s.save(p);

		s.beginTransaction().commit();
		s.close();

	}

}

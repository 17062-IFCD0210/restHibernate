package com.ipartek.formacion.ejemplos.restHibernate.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "perro")
public class Perro {

	@Id
	@GeneratedValue
	private int Id;// clave y se genera automaticamente

	/* Persistente, un tipo basico (string) */
	@Basic
	@Column(name = "nombre")
	private String nombre;

	@Basic
	@Column(name = "rabo")
	private String rabo;

	/**
	 * @param nombre
	 * @param rabo
	 */
	public Perro(String nombre, String rabo) {
		super();
		this.nombre = nombre;
		this.rabo = rabo;
	}

	public Perro() {
		super();
		nombre = "";
		rabo = "";
	}

	public String getNombre() {
		return nombre;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRabo() {
		return rabo;
	}

	public void setRabo(String rabo) {
		this.rabo = rabo;
	}

	@Override
	public String toString() {
		return "Perro [nombre=" + nombre + ", rabo=" + rabo + "]";
	}

}

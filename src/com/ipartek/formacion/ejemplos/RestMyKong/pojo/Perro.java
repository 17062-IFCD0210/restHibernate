package com.ipartek.formacion.ejemplos.RestMyKong.pojo;

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
	Long id;

	@Basic
	@Column(name = "nombre")
	private String nombre;

	@Basic
	@Column(name = "raza")
	private String raza;

	/**
	 * @param nombre
	 * @param raza
	 */
	public Perro(String nombre, String raza) {
		super();
		this.nombre = nombre;
		this.raza = raza;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getRaza() {
		return this.raza;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Perro [nombre=" + this.nombre + ", raza=" + this.raza + "]";
	}

}

package com.et.backend.clientes.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table( name = "clientes" )
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull( message = "El campo 'nombre' no debe ser nulo" )
	@NotBlank( message = "El campo 'nombre' no debe estar vacio" )
	private String nombre;

	@NotBlank( message = "El campo 'apellidoPaterno' no debe estar vacio" )
	@Column( name = "apellido_paterno" )
	private String apellidoPaterno;

	//@NotNull( message="El campo 'apellidoMaterno' no debe ser nulo" )
	@NotNull( message = "El campo 'apellidoMaterno' no debe ser nulo" )
	@Column( name = "apellido_materno" )
	private String apellidoMaterno;

	private String ci;

	@Pattern( regexp="^[0-9]+$",
			message="En el campo 'edad' solo se permiten numeros" )
	private String edad;
	private Long numero;

	@Email
	@Column( name = "email", unique = true )
	private String email;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}
}

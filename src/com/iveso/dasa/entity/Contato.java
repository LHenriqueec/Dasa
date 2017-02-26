package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contato {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String telefone;
	private String celular;
	private String email;
	private String responsavel;

	public Integer getId() {
		return id;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public String getCelcular() {
		return celular;
	}

	public String getEmail() {
		return email;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setCelcular(String celcular) {
		this.celular = celcular;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel.toUpperCase();
	}
}

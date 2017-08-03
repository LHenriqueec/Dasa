package com.iveso.dasa.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Contato {

	private String telefone;
	private String celular;
	private String email;
	private String responsavel;

	public String getTelefone() {
		return telefone;
	}

	public String getCelular() {
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

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel.toUpperCase();
	}
}

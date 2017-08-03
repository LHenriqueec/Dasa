package com.iveso.dasa.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Endereco {

	private String logradouro;
	private String cidade;
	private String bairro;
	
	@Embedded
	private Estado estado;
	
	public Endereco() {
		this.estado = new Estado();
	}
	
	public Endereco(Estado estado) {
		this.estado = estado;
	}
	

	public String getLogradouro() {
		return logradouro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro.toUpperCase();
	}

	public void setCidade(String cidade) {
		this.cidade = cidade.toUpperCase();
	}

	public void setBairro(String bairro) {
		this.bairro = bairro.toUpperCase();
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}

package com.iveso.dasa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Endereco {

	@Id
	@GeneratedValue
	private Integer id;
	private String logradouro;
	private String cidade;
	private String bairro;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Estado estado;
	
	public Endereco() {}
	
	public Endereco(Estado estado) {
		this.estado = estado;
	}
	

	public Integer getId() {
		return id;
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

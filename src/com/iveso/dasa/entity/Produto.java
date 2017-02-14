package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Produto {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String codigo;
	private String nome;

	public Integer getId() {
		return id;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
}

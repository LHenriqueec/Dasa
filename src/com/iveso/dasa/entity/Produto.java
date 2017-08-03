package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Produto {

	@Id
	private String codigo;
	private String nome;
	
	public Produto() {
	}
	
	public Produto(String codigo, String nome) {
		this();
		this.codigo = codigo;
		this.nome = nome.toUpperCase();
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(getClass() != obj.getClass()) 
			return false;
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(codigo)
		.append(" - ")
		.append(nome);
		return buff.toString();
	}
}











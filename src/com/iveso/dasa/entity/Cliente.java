package com.iveso.dasa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cliente {

	@Id
	private String cnpj;
	private String nome;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Contato contato;
	
	public Cliente() {}
	
	public Cliente(Endereco endereco, Contato contato) {
		this.endereco = endereco;
		this.contato = contato;
	}
	

	public String getCnpj() {
		return cnpj;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public Contato getContato() {
		return contato;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void setContato(Contato contato) {
		this.contato = contato;
	}
}

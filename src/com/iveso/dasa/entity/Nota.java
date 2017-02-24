package com.iveso.dasa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Nota {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String numeroNota;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Produto> produtos;
	
	public Nota() {
		produtos = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}

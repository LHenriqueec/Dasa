package com.iveso.dasa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Nota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String numeroNota;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Item> itens;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Recibo> recibos;
	
	public Nota() {
		itens = new ArrayList<>();
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Item> getItens() {
		return itens;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}
	
	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}
}

package com.iveso.dasa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Nota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String numero;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy="nota", fetch=FetchType.EAGER)
	private List<ItemNota> itens;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Recibo> recibos;
	
	public Nota() {
		itens = new ArrayList<>();
	}

	public String getNumeroNota() {
		return numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemNota> getItens() {
		return itens;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}
	
	public void setNumeroNota(String numeroNota) {
		this.numero = numeroNota;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setItens(List<ItemNota> itens) {
		this.itens = itens;
	}

	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}
}

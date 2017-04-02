package com.iveso.dasa.entity;

import java.time.LocalDate;
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
public class Recibo {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Item> itens;

	private String numero;
	private LocalDate data;
	private boolean gerado;
	
	public Recibo() {
		itens = new ArrayList<>();
		data = LocalDate.now();
	}

	public Integer getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public LocalDate getData() {
		return data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Item> getItens() {
		return itens;
	}
	
	public boolean isGerado() {
		return gerado;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public void setGerado(boolean gerado) {
		this.gerado = gerado;
	}
}

package com.iveso.dasa.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class Recibo {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Id
	private String numero;

	@ManyToOne
	private Cliente cliente;

	@ManyToMany
	private List<Nota> notas;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Item> itens;
	private LocalDate data;
	private boolean gerado;
	
	public Recibo() {
		notas = new ArrayList<>();
		itens = new ArrayList<>();
		data = LocalDate.now();
		
		
	}

	public String getNumero() {
		return numero;
	}

	public String getData() {
		return data.format(formatter);
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public List<Nota> getNotas() {
		return notas;
	}

	public List<Item> getItens() {
		return itens;
	}
	
	public boolean isGerado() {
		return gerado;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setData(String data) {
		this.data = LocalDate.parse(data, formatter);
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public void setGerado(boolean gerado) {
		this.gerado = gerado;
	}
}

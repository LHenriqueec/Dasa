package com.iveso.dasa.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Recibo implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Id
	private String numero;

	@ManyToOne
	private Cliente cliente;

	@OneToMany(mappedBy="recibo", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<ItemRecibo> itens;
	
	private LocalDate data;
	private boolean gerado;
	
	public Recibo() {
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
	
	public List<ItemRecibo> getItens() {
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
	
	public void setItens(List<ItemRecibo> itens) {
		this.itens = itens;
	}
	
	public void setGerado(boolean gerado) {
		this.gerado = gerado;
	}
}

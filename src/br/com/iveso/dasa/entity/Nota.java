package br.com.iveso.dasa.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Nota {

	@Id
	private String numero;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@ManyToOne()
	private Cliente cliente;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="nota")
	private List<ItemNota> itens;

	public String getNumero() {
		return numero;
	}

	public Date getDate() {
		return data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemNota> getItens() {
		return itens;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setDate(Date date) {
		this.data = date;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setItens(List<ItemNota> itens) {
		this.itens = itens;
	}
	
	
}
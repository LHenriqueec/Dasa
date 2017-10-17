package br.com.iveso.dasa.entity;

import java.util.ArrayList;
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

	public Nota () {}
	
	//TODO: Remover construr. Criado apenas paar teste
	public Nota (String numero) {
		this.numero = numero;
		itens = new ArrayList<>();
	}
	
	public String getNumero() {
		return numero;
	}

	public Date getData() {
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

	public void setData(Date data) {
		this.data = data;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setItens(List<ItemNota> itens) {
		this.itens = itens;
	}
	
	public ItemNota getItemByCodigoProduto(String codigo) {
		return itens.stream().filter(item -> item.getProduto().getCodigo().equals(codigo)).findFirst().get();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nota other = (Nota) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
}

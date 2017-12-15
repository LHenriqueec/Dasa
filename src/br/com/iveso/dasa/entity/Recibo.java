package br.com.iveso.dasa.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe de Recibo
 * @author Luiz Henrique
 */
@Entity
public class Recibo {
	
	@Id
	private String numero;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@ManyToOne()
	private Cliente cliente;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="recibo")
	private List<ItemRecibo> itens;
	
	private boolean printer;

	public String getNumero() {
		return numero;
	}

	public Date getData() {
		return data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemRecibo> getItens() {
		return itens;
	}
	
	public boolean isPrinter() {
		return printer;
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

	public void setItens(List<ItemRecibo> itens) {
		this.itens = itens;
	}
	
	public void setPrinter(boolean printer) {
		this.printer = printer;
	}
	
	public void addItem(ItemRecibo item) {
		item.setRecibo(this);
		itens.add(item);
	}
	
	public void removeItem(Item item) {
		item.getProduto().creditar(item.getQuantidade());
	}
	
	public boolean contem(Item item) {
		return this.itens.contains(item);
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
		Recibo other = (Recibo) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
}

package com.iveso.dasa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Pedido {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Nota nota;
	
	@OneToOne(cascade=CascadeType.ALL)
	private ProdutoPedido pedidoProduto;
	
	public Pedido() {
		this.nota = new Nota();
		this.pedidoProduto = new ProdutoPedido();
	}

	public Nota getNota() {
		return nota;
	}

	public ProdutoPedido getPedidoProduto() {
		return pedidoProduto;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public void setPedidoProduto(ProdutoPedido pedido) {
		this.pedidoProduto = pedido;
	}
	
	
}

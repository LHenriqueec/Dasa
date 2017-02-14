package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProdutoPedido {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	
	@ManyToOne
	private Produto produto;
	private Integer quantidade;

	public Integer getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		ProdutoPedido prodPedido = (ProdutoPedido) obj;
		return this.id == prodPedido.getId() || this.id.equals(prodPedido.getId()) ? true : false;
	}
}

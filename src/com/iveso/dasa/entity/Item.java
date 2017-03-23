package com.iveso.dasa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Item {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Produto produto;
	
	private Integer quantidade;
	
	public Item() {
		this.produto = new Produto();
	}

	public Produto getProduto() {
		return produto;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}

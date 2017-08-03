package com.iveso.dasa.entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	protected Produto produto;
	protected int quantidade;
	
	public Produto getProduto() {
		return produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}

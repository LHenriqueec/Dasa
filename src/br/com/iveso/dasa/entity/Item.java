package br.com.iveso.dasa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Item {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	protected Produto produto;
	protected int quantidade;
	
	public Item() {}
	
	public Item(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

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
	
	public void creditar(int quantidade) {
		produto.debitar(quantidade);
		this.quantidade += quantidade;
	}
	
	public void debitar(int quantidade) {
		produto.creditar(quantidade);
		this.quantidade -= quantidade;
	}
	
	@Override
	public String toString() {
		return produto + "\t - \t" + quantidade;
	}
}

package com.iveso.dasa.entity;

import javax.persistence.*;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Produto produto;
	
	private Integer quantidade;
	
	private StringBuffer dados;
	
	public Item() {
		dados = new StringBuffer();
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public StringBuffer getDados() {
		return dados;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public void add(String numNota, int quantidade) {
		if (dados.length() > 0) dados.append('-');
		dados.append(numNota);
		dados.append(':');
		dados.append(quantidade);
	}
}

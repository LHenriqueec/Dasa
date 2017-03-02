package com.iveso.dasa.entity;

import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Pedido {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Nota nota;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Produto produto;
	
	@Transient
	private int quantidade;
	
	public Pedido() {
		this.produto = new Produto();
		this.nota = new Nota();
	}

	public Nota getNota() {
		return nota;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public void setProduto(Produto produto) {
		this.produto.setCodigo(produto.getCodigo());
		this.produto.setNome(produto.getNome());
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
		Produto produtoNota = nota.getProdutos().stream().filter(produto -> produto.equals(this.produto))
					.collect(Collectors.toList()).get(0);
		
		produtoNota.setQuantidade(produtoNota.getQuantidade() - this.quantidade);
		produto.setQuantidade(this.quantidade);
	}
}

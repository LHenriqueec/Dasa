package com.iveso.dasa.entity;

import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pedido {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Nota nota;
	
	private Produto produto;
	
	public Pedido() {
		this.nota = new Nota();
	}

	public Nota getNota() {
		return nota;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public void inserirProduto(Produto produto) {
		this.produto = new Produto(produto.getCodigo(), produto.getNome());
	}
	
	public void inserirQuantidade(int quantidade) {
		Produto produtoNota = nota.getProdutos().stream().filter(produto -> produto.equals(this.produto))
					.collect(Collectors.toList()).get(0);
		
		produtoNota.setQuantidade(produtoNota.getQuantidade() - quantidade);
		produto.setQuantidade(quantidade);
	}
}

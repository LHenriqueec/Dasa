package br.com.iveso.dasa.entity;

public class Item {

	private Produto produto;
	private Integer quantidade;
	
	public Item() {}
	
	public Item(Produto produto, Integer quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
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
	
	@Override
	public String toString() {
		return produto + "\t - \t" + quantidade;
	}
}

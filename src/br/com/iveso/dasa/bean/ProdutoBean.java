package br.com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.iveso.dasa.entity.Produto;

@Named
@SessionScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private List<Produto> produtos; 
	private boolean isEdit = false;
	
	@PostConstruct
	private void start() {
		this.produto = new Produto();
		this.produtos = new ArrayList<>();
	}

	public void salvar() {
		if (isEdit) {
			
		} else {
			this.produtos.add(produto);
		}
		this.produto = new Produto();
		this.isEdit = false;
	}
	
	public void alterar(Produto produto) {
		this.produto = produto;
		this.isEdit = true;
	}
	
	public void deletar(Produto produto) {
		produtos.remove(produto);
		this.produto = new Produto();
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
}

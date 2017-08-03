package com.iveso.dasa.dao.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.service.ProdutoService;

@Named
@SessionScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoService service;

	private boolean edit = false;
	List<Produto> produtos = null;
	private Produto produto;

	@PostConstruct
	private void init() {
		this.produto = new Produto();
		produtos = service.todosProdutos();
	}

	public void salvar() {
		if (edit) {
			service.alterar(produto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto alterado com sucesso!"));
		} else {
			service.salvar(produto);
			produtos.add(produto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto salvo com sucesso!"));
		}
		produto = new Produto();
		edit = false;
	}

	public void alterar(Produto produto) {
		this.produto = produto;
		edit = true;
	}

	public void deletar(Produto produto) {
		produtos.remove(produto);
		service.deletar(produto);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto excluído com sucesso!"));
		
		this.produto = new Produto();
		this.edit = false;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public Produto getProduto() {
		return produto;
	}
}

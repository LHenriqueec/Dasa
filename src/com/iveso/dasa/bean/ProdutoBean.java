package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.service.ProdutoService;
import com.iveso.dasa.service.ServiceException;

@Named
@SessionScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoService service;

	private List<Produto> produtos;
	private Produto produto;
	private boolean edit;
	private int index;

	public ProdutoBean() {
		produtos = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		try {
			produtos.addAll(service.listarProdutos());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void novo_produto() {
		produto = new Produto();
		openDialog();
	}

	public void salvar() {
		try {
			if (edit) {
				service.alterar(produto);
				produtos.set(index, produto);
				edit = false;
			} else {
				service.salvar(produto);
				produtos.add(produto);
			}
		} catch (ServiceException e) {
			produtos.remove(produto);
			e.printStackTrace();
		}
		produto = null;
	}
	
	public void salvar_exit() {
		RequestContext.getCurrentInstance().closeDialog(produto);
	}
	
	public void deletar(Produto produto) {
		try {
			produtos.remove(produto);
			service.deletar(produto);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void editar(Produto produto) {
		this.produto = produto;
		index = produtos.indexOf(produto);
		edit = true;
		openDialog();
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	private void openDialog() {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("resizable", false);

		RequestContext.getCurrentInstance().openDialog("novo_produto", options, null);
	}
}

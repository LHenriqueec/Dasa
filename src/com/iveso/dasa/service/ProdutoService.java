package com.iveso.dasa.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ProdutoDAO;
import com.iveso.dasa.entity.Produto;

@Dependent
public class ProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO dao;

	public List<Produto> todosProdutos() {
		List<Produto> produtos = null;
		try {
			produtos = dao.getProdutos();
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return produtos;
	}

	@Transactional
	public void salvar(Produto produto) {
		try {
			dao.salvar(produto);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void alterar(Produto produto) {
		try {
			dao.alterar(produto);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void deletar(Produto produto) {
		try {
			Produto produtoDB = dao.carregar(produto.getCodigo(), Produto.class);
			dao.deletar(produtoDB);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}

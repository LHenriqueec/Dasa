package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.Produto;

public class ProdutoDAO extends DAO<Produto> {

	public ProdutoDAO() {
		super(Produto.class);
	}
	
	public List<Produto> carregarProdutos() throws DAOException {
		return list("from Produto");
	}

}

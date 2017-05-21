package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Produto;

public class ProdutoDAO extends DAO {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Produto> getProdutos() throws DAOException {
		return query("SELECT p FROM Produto p").getResultList();
	}
}

package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Nota;

public class NotaDAO extends DAO {
	private static final long serialVersionUID = 1L;

	public List<Nota> getNotas() throws DAOException {
		return query("FROM Nota", Nota.class).getResultList();
	}

	public List<Nota> notaByProduto(String produto) throws DAOException {
		return query("FROM Nota n where n.itens.produto.codigo like :produto", Nota.class)
		.setParameter("produto", produto).getResultList();
	}
}

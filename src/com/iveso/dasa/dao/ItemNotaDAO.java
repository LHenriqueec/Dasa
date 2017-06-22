package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.ItemNota;

public class ItemNotaDAO extends DAO {
	private static final long serialVersionUID = 1L;
	
	public List<ItemNota> getItemByProduto(String produto) throws DAOException {
		return query("from ItemNota n where n.produto.codigo like :produto", ItemNota.class)
				.setParameter("produto", produto).getResultList();
	}
}

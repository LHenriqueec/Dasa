package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.ItemNota;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;

public class ItemNotaDAO extends DAO {
	private static final long serialVersionUID = 1L;
	
	public List<ItemNota> getItemByProduto(String produto) throws DAOException {
		return query("from ItemNota n where n.produto.codigo like :produto", ItemNota.class)
				.setParameter("produto", produto).getResultList();
	}
	
	public ItemNota getItemByNotaAndProduto(Nota nota, Produto produto) throws DAOException {
		return query("from ItemNota n where n.nota like :nota and n.produto like :produto", ItemNota.class)
				.setParameter("nota", nota)
				.setParameter("produto", produto)
				.getSingleResult();
	}
}

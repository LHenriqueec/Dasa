package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Produto;

public class ItemNotaDAO extends DAO<ItemNota> {

	public ItemNotaDAO() {
		super(ItemNota.class);
	}
	
	public List<ItemNota> buscarItensByProduto(Produto produto) throws DAOException {
		return query("from ItemNota i where i.produto = :produto")
				.setParameter("produto", produto).getResultList();
	}
	
	public ItemNota buscarItemByProduto(String search) throws DAOException {
		Object[] result = null;
		try {
			int codigo = Integer.parseInt(search);
			result = (Object[]) em.createQuery("select produto, sum(item.quantidade) as quantidade from ItemNota item inner join Produto produto"
					+ " on item.produto = produto where produto.codigo like :codigo group by produto")
					.setParameter("codigo", "%" + String.valueOf(codigo) + "%").getSingleResult();
		} catch (NumberFormatException e) {
			result = (Object[]) em.createQuery("select produto, sum(item.quantidade) as quantidade from ItemNota item inner join Produto produto"
					+ " on item.produto = produto where produto.nome like :nome group by produto")
					.setParameter("nome", "%" + search + "%").getSingleResult();
		}
		
		
		
		return new ItemNota((Produto) result[0], Integer.valueOf(result[1].toString()));
	}
}

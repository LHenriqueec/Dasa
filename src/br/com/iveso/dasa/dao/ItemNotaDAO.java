package br.com.iveso.dasa.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Produto;

public class ItemNotaDAO extends DAO<ItemNota> {

	public ItemNotaDAO() {
		super(ItemNota.class);
	}
	
	public ItemNota buscarItemByNotaItemRecibo(ItemRecibo item) throws DAOException {
		return query("from ItemNota i where i.nota = :nota and i.produto = :produto").
				setParameter("nota", item.getNota())
				.setParameter("produto", item.getProduto()).getSingleResult();		
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
	
	public List<ItemNota> carregarItens() throws DAOException {
		List<ItemNota> itens = new ArrayList<>();
		Iterator<?> iterator = em.createQuery("select produto, sum(item.quantidade) as quantidade from ItemNota item group by item.produto")
			.getResultList().iterator();
		
		while (iterator.hasNext()) {
			Object[] result = (Object[]) iterator.next();
			
			itens.add(new ItemNota((Produto) result[0], 
					Integer.valueOf(result[1].toString())));
		}
		
		return itens;
	}
}

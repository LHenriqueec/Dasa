package br.com.iveso.dasa.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Produto;

public class ItemNotaDAO extends DAO<ItemNota> {

	public ItemNotaDAO() {
		super(ItemNota.class);
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

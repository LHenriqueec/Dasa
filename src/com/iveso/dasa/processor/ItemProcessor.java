package com.iveso.dasa.processor;

import java.util.ArrayList;
import java.util.List;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ItemNotaDAO;
import com.iveso.dasa.entity.ItemNota;
import com.iveso.dasa.entity.ItemRecibo;

public class ItemProcessor extends Processor {
	
	private ItemRecibo item;
	
	public ItemProcessor(ItemRecibo item) {
		this.item = item;
	}

	public List<ItemRecibo> processorItem(ItemNotaDAO dao) throws DAOException {
		List<ItemRecibo> itensRecibo = new ArrayList<>();
		List<ItemNota> itens = dao.getItemByProduto(item.getProduto().getCodigo());
		ItemRecibo otherItem = null;

		int qtdRecibo = item.getQuantidade();
		int i = 0;
		while (qtdRecibo > 0) {
			ItemNota itemNota = itens.get(i);
			int qtdNota = itemNota.getQuantidade();

			if (qtdNota >= qtdRecibo) {
				otherItem = new ItemRecibo(item.getRecibo(), itemNota.getNota(), item.getProduto(), qtdRecibo);
				qtdRecibo -= qtdRecibo;
			} else {
				otherItem = new ItemRecibo(item.getRecibo(), itemNota.getNota(), item.getProduto(), qtdNota);
				qtdRecibo -= qtdNota;
			}
			itensRecibo.add(otherItem);
			i++;
		}

		return itensRecibo;
	}

	public void salvar(List<ItemNota> itens, ItemNotaDAO itemDAO) {
		// TODO: Sincronizar as mudanças feitas nos Itens das Notas no banco
	}
}

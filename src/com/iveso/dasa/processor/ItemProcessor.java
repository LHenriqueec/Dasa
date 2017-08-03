package com.iveso.dasa.processor;

import java.util.ArrayList;
import java.util.List;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ItemNotaDAO;
import com.iveso.dasa.entity.ItemNota;
import com.iveso.dasa.entity.ItemRecibo;

public class ItemProcessor extends Processor {

	private ItemRecibo item;

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
				itensRecibo.add(otherItem);
			} else {
				if (qtdNota != 0)
					otherItem = new ItemRecibo(item.getRecibo(), itemNota.getNota(), item.getProduto(), qtdNota);
				qtdRecibo -= qtdNota;
				itensRecibo.add(otherItem);
			}
			i++;
		}

		return itensRecibo;
	}

	public void processarItens(List<ItemRecibo> itens, ItemNotaDAO itemDAO, String opcao) throws DAOException {
		for (ItemRecibo item : itens) {
			ItemNota itemNota = itemDAO.getItemByNotaAndProduto(item.getNota(), item.getProduto());

			if (opcao.equals("salvar"))
				itemNota.debitar(item.getQuantidade());
			if (opcao.equals("deletar")) 
				itemNota.creditar(item.getQuantidade());
		}
	}

	public void processarItensAlterados(List<ItemRecibo> itensRecibo, List<ItemRecibo> itensView) {
		int qtdRecibo = 0;
		int qtdView = 0;

		int i = 0;
		for (ItemRecibo itemV : itensView) {
			qtdView = itemV.getQuantidade();
			while (qtdView > 0) {
				ItemRecibo itemR = itensRecibo.get(i);

				if (itemR.getProduto().equals(itemV.getProduto())) {
					qtdRecibo = itemR.getQuantidade();

					qtdView -= qtdRecibo;
				}
			}
		}
	}

	public ItemProcessor setItem(ItemRecibo item) {
		this.item = item;

		return this;
	}
}

package br.com.iveso.dasa.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Recibo;

public class ReciboProcessor {

	private static ReciboProcessor instance;
	private List<ItemNota> itensNota;
	private List<ItemRecibo> itensRecibo;
	private Recibo recibo;

	public static ReciboProcessor getInstance() {
		if (instance == null)
			instance = new ReciboProcessor();
		return instance;
	}

	public void processarSalvamento(Recibo recibo) throws ProcessorException {
		this.recibo = recibo;
		Iterator<ItemRecibo> iterator = recibo.getItens().iterator();
		itensRecibo = new ArrayList<>();
		while (iterator.hasNext()) {

			ItemRecibo item = iterator.next();
			instance.buscarItensByProduto(item);
			instance.debitarQuantidade(item);
			item.setRecibo(recibo);
		}
		recibo.setItens(itensRecibo);
	}

	private void buscarItensByProduto(ItemRecibo item) throws ProcessorException {
		try {
			ItemNotaDAO itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			this.itensNota = itemDAO.buscarItenByProduto(item.getProduto());
			
		} catch (DAOException e) {
			throw new ProcessorException(e);
		}
	}
	
	private void debitarQuantidade(ItemRecibo item) throws ProcessorException {
		int quantidade = item.getQuantidade();

		Iterator<ItemNota> iNota = itensNota.iterator();

		while (quantidade > 0 && iNota.hasNext()) {
			ItemNota itemNota = iNota.next();
			ItemRecibo itemRecibo = null;

			if (itemNota.getQuantidade() >= quantidade) {
				itemNota.debitar(quantidade);
				itemRecibo = new ItemRecibo(recibo, item.getProduto(), quantidade, itemNota.getNota());
				quantidade = 0;
			} else {
				itemRecibo = new ItemRecibo(recibo, item.getProduto(), itemNota.getQuantidade(), itemNota.getNota());
				quantidade -= itemNota.getQuantidade();
				itemNota.setQuantidade(0);
			}

			itensRecibo.add(itemRecibo);
		}
	}
	
	public void processarExclusao(Recibo recibo) throws ProcessorException {
		Iterator<ItemRecibo> iterator = recibo.getItens().iterator();

		while (iterator.hasNext()) {
			ItemRecibo itemRecibo = iterator.next();
			instance.creditarQuantidade(itemRecibo);
		}
	}

	public void creditarQuantidade(ItemRecibo item) throws ProcessorException {
		try {
			ItemNotaDAO itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			itemDAO.buscarItemByNotaItemRecibo(item).creditar(item.getQuantidade());
		} catch (DAOException e) {
			throw new ProcessorException(e);
		}
	}


	// TODO: Deletar métodos. Criados para Teste

	public void processarExclusao(Recibo recibo, List<ItemNota> itensNota) {
		Iterator<ItemRecibo> iterator = recibo.getItens().iterator();

		while (iterator.hasNext()) {
			ItemRecibo itemRecibo = iterator.next();
			instance.creditarQuantidade(itemRecibo, itensNota);
		}
	}

	public void creditarQuantidade(ItemRecibo item, List<ItemNota> itensNota) {
		// O filtro é feito no Banco de Dados retornando somente a ItemNota desejado
		itensNota.stream()
				.filter(itemNota -> itemNota.getProduto().equals(item.getProduto()) && itemNota.getNota().equals(item.getNota()))
				.collect(Collectors.toList()).get(0).creditar(item.getQuantidade());
	}
	

	public void processar(Recibo recibo, List<ItemNota> itens) throws ProcessorException {
		Iterator<ItemRecibo> iterator = recibo.getItens().iterator();
		itensRecibo = new ArrayList<>();
		while (iterator.hasNext()) {

			ItemRecibo item = iterator.next();
			instance.buscarItensByProduto_teste(item, itens);
			instance.debitarQuantidade_teste(item);
			item.setRecibo(recibo);
		}
		recibo.setItens(itensRecibo);
	}

	private void buscarItensByProduto_teste(ItemRecibo item, List<ItemNota> itens) throws ProcessorException {
		this.itensNota = itens.stream().filter(itemNota -> itemNota.getProduto().equals(item.getProduto()))
				.collect(Collectors.toList());
	}

	private void debitarQuantidade_teste(ItemRecibo item) {

		int quantidade = item.getQuantidade();

		Iterator<ItemNota> iNota = itensNota.iterator();

		while (quantidade > 0 && iNota.hasNext()) {
			ItemNota itemNota = iNota.next();
			ItemRecibo itemRecibo = null;

			if (itemNota.getQuantidade() >= quantidade) {
				itemNota.debitar(quantidade);
				itemRecibo = new ItemRecibo(item.getProduto(), quantidade, itemNota.getNota());
				quantidade = 0;
			} else {
				itemRecibo = new ItemRecibo(item.getProduto(), itemNota.getQuantidade(), itemNota.getNota());
				quantidade -= itemNota.getQuantidade();
				itemNota.setQuantidade(0);
			}

			itensRecibo.add(itemRecibo);
		}
	}
}

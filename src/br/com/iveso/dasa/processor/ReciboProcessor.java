package br.com.iveso.dasa.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;

public class ReciboProcessor {

	private static ReciboProcessor instance;
//	private List<ItemNota> itensNota;
	private List<ItemRecibo> itensRecibo;
	private Recibo recibo;

	public static ReciboProcessor getInstance() {
		if (instance == null)
			instance = new ReciboProcessor();
		return instance;
	}
	
	public void processarEdicao(Recibo recibo, List<ItemRecibo> itensNovos) throws ProcessorException {
		List<ItemRecibo> itens = new ArrayList<>();
		
		for(ItemRecibo newItem : itensNovos) {
			List<ItemRecibo> itensRecibo = filtrarProdutoRecibo(newItem.getProduto(), recibo);
			itens.addAll(atualizarItens(itensRecibo, newItem));
		}
		
		recibo.setItens(itens);
	}

	public void processarSalvamento(Recibo recibo) throws ProcessorException {
		this.recibo = recibo;
		Iterator<ItemRecibo> iterator = recibo.getItens().iterator();
		itensRecibo = new ArrayList<>();
		while (iterator.hasNext()) {

			ItemRecibo item = iterator.next();
			instance.debitarQuantidadeNota(item);
			item.setRecibo(recibo);
		}
		recibo.setItens(itensRecibo);
	}
	
	public void processarExclusao(Recibo recibo) throws ProcessorException {
		Iterator<ItemRecibo> iterator = recibo.getItens().iterator();

		while (iterator.hasNext()) {
			ItemRecibo itemRecibo = iterator.next();
			instance.creditarQuantidadeNota(itemRecibo);
		}
	}
	
	private List<ItemRecibo> filtrarProdutoRecibo(Produto produto, Recibo recibo) {
		return recibo.getItens().stream().filter(item -> produto.equals(item.getProduto()))
				.collect(Collectors.toList());
	}
	
	private List<ItemNota> buscarItensByProduto(Produto produto) throws ProcessorException {
		List<ItemNota> itensNota = null;
		try {
			ItemNotaDAO itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			itensNota = itemDAO.buscarItensByProduto(produto);
			
		} catch (DAOException e) {
			throw new ProcessorException(e);
		}
		
		return itensNota;
	}
	
	private List<ItemRecibo> atualizarItens(List<ItemRecibo> itens, ItemRecibo newItem) throws ProcessorException {
		int totalAtualRecibo = calcularTotalItens(itens);
		
		List<ItemRecibo> itensNovos = new ArrayList<>();
		
		for(ItemRecibo itemRecibo : itens) {
			if(newItem.getQuantidade() < totalAtualRecibo) {
				int quantidade = totalAtualRecibo - newItem.getQuantidade();
				itensNovos.addAll(debitarQuantidade(quantidade, itemRecibo));
			} else {
				int quantidade = newItem.getQuantidade() - totalAtualRecibo;
				itensNovos.addAll(creditarQuantidade(quantidade, itemRecibo));
			}
		}
	
		return itensNovos;
	}
	
	private int calcularTotalItens(List<ItemRecibo> itens) {
		return itens.stream().mapToInt(ItemRecibo::getQuantidade).sum();
	}
	
	private void creditarQuantidadeNota(ItemRecibo item) throws ProcessorException {
		try {
			ItemNotaDAO itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			itemDAO.buscarItemByNotaItemRecibo(item).creditar(item.getQuantidade());
		} catch (DAOException e) {
			throw new ProcessorException(e);
		}
	}
	private List<ItemRecibo> creditarQuantidade(int quantidade, ItemRecibo item) throws ProcessorException {
		try {
			//TODO: Verificar se está funcionando
			List<ItemRecibo> itens = new ArrayList<>();
			
			List<ItemNota> itensNota =  DAOFactory.getInstance().getDAO(ItemNotaDAO.class).buscarItensByProduto(item.getProduto());
			ItemNota itemNota = itensNota.stream().filter(itemN -> itemN.getProduto().equals(item.getProduto())).findFirst().get();
			itensNota.remove(itemNota);
			
			Iterator<ItemNota> iterator = itensNota.iterator();
			while(quantidade > 0) {
				if(itemNota.getQuantidade() >= quantidade) {
					itemNota.debitar(quantidade);
					itens.add(new ItemRecibo(item.getRecibo(), item.getProduto(), quantidade + item.getQuantidade(), itemNota.getNota()));
					quantidade = 0;
				} else {
					itens.add(new ItemRecibo(item.getRecibo(),itemNota.getProduto(), itemNota.getQuantidade(), itemNota.getNota()));
					quantidade -= itemNota.getQuantidade();
					itemNota.setQuantidade(0);
					itemNota = iterator.next();
				}
			}
		
			return itens;
		} catch(DAOException e) {
			throw new ProcessorException(e);
		}
	}
	
	private void debitarQuantidadeNota(ItemRecibo item) throws ProcessorException {
		int quantidade = item.getQuantidade();
		
		Iterator<ItemNota> iNota = buscarItensByProduto(item.getProduto()).iterator();
		
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
	
	
	
	private List<ItemRecibo> debitarQuantidade(int quantidade, ItemRecibo item) {
		//TODO: Corrigir método
		// Buscar ItemNota do Banco
//		ItemNota itemNota = notas.stream().filter(nota -> item.getNota().equals(nota)).findFirst().get()
//				.getItens().stream().filter(itemN -> itemN.getProduto().equals(item.getProduto()))
//				.findFirst().get();
		
//		itemNota.creditar(quantidade);
		return Arrays.asList(new ItemRecibo(item.getRecibo(), item.getProduto(), quantidade, item.getNota()));
	}
}

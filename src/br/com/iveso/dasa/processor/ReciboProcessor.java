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

/**
 * Processador de Recibos
 * @author Luiz Henrique
 */
public class ReciboProcessor {

	private static ReciboProcessor instance;
	// private List<ItemNota> itensNota;
	private List<ItemRecibo> itensRecibo;
	private Recibo recibo;

	public static ReciboProcessor getInstance() {
		if (instance == null)
			instance = new ReciboProcessor();
		return instance;
	}

	/**
	 * Processa a atualização dos novos Itens no Recibo
	 * @param recibo Recibo que terá os valores atualizados
	 * @param itensNovos Novos itens que serão processados no Recibo
	 * @throws ProcessorException
	 */
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

	/**
	 * Filtra os itens do Recibo baseado em um Produto 
	 * @param produto {@link Produto} que será utilizado como filtro
	 * @param recibo {@link Recibo} que contém a lista de itens para serem filtrados
	 * @return Lista com itens filtrados
	 */
	private List<ItemRecibo> filtrarProdutoRecibo(Produto produto, Recibo recibo) {
		return recibo.getItens().stream().filter(item -> produto.equals(item.getProduto()))
				.collect(Collectors.toList());
	}

	/**
	 * Faz o processamento do novo Item 
	 * @param itens Lista com itens filtrados por {@link Produto}
	 * @param newItem Novo Item que será processado
	 * @return Lista com itens processados
	 * @throws ProcessorException
	 */
	private List<ItemRecibo> atualizarItens(List<ItemRecibo> itens, ItemRecibo newItem) throws ProcessorException {
		int quantidadeAtual = calcularTotalItens(itens);
		int quantidadeNova = newItem.getQuantidade();

		List<ItemRecibo> itensNovos = new ArrayList<>();

		if (quantidadeAtual == quantidadeNova)
			return itens;

		// Debita a diferença
		if (quantidadeAtual > quantidadeNova) {
			int quantidade = quantidadeAtual - quantidadeNova;
			itensNovos.addAll(debitarQuantidadeRecibo(quantidade, itens));

		// Credita a diferença
		} else {
			int quantidade = quantidadeNova - quantidadeAtual;
			itensNovos.addAll(creditarQuantidadeRecibo(quantidade, itens));
		}

		return itensNovos;
	}

	/**
	 * Calcula a quantidade total de itens
	 * @param itens Lista de itens
	 * @return Quantidade total de itens
	 */
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
	
	private List<ItemRecibo> creditarQuantidadeRecibo(int quantidade, List<ItemRecibo> itens) throws ProcessorException {
		// Filtra os itensNota que possuem o mesmo produto do itemRecibo
		// Isso será feito pelo banco de Dados
		List<ItemNota> itensNotaFiltrados = buscarItensByProduto(itens.get(0).getProduto());
		// Ordena os itens com o Número das Notas em ordem decrescente
		itens.sort((item1, item2) -> item2.getNota().getNumero().compareTo(item1.getNota().getNumero()));

		// Verifica se a Nota do último item, que é o primeiro da lista, tem saldo suficiente
		ItemRecibo item = itens.get(0);
		ItemNota itemNota = itensNotaFiltrados.stream()
				.filter(iNota -> iNota.getNota().getNumero().equals(item.getNota().getNumero())).findFirst().get();

		if (itemNota.getQuantidade() >= quantidade) {
			itemNota.debitar(quantidade);
			item.creditar(quantidade);
			quantidade = 0;
		} else {
			// Usa apenas itensNota que não estão sendo uzadas pelos Itens do Recibo
			itensNotaFiltrados.remove(itemNota);
			
			Iterator<ItemNota> iterator = itensNotaFiltrados.iterator(); 
			
			quantidade -= itemNota.getQuantidade();
			item.creditar(itemNota.getQuantidade());
			itemNota.setQuantidade(0);
			
			while(quantidade > 0) {
				itemNota = iterator.next();
				
				if(itemNota.getQuantidade() >= quantidade) {
					itemNota.debitar(quantidade);
					itens.add(new ItemRecibo(item.getRecibo(), item.getProduto(), quantidade, itemNota.getNota()));
					quantidade = 0;
				} else {
					quantidade -= itemNota.getQuantidade();
					itens.add(new ItemRecibo(item.getRecibo(), item.getProduto(), itemNota.getQuantidade(), itemNota.getNota()));
					itemNota.setQuantidade(0);
				}
			}
		}

		return itens;
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

	private List<ItemRecibo> debitarQuantidadeRecibo(int quantidade, List<ItemRecibo> itens) throws ProcessorException {
		List<ItemRecibo> itensAtualizados = new ArrayList<>();

		// Ordena os itens com o Número das Notas em ordem decrescente
		itens.sort((item1, item2) -> item2.getNota().getNumero().compareTo(item1.getNota().getNumero()));
		Iterator<ItemRecibo> iterator = itens.iterator();

		do {
			ItemRecibo item = iterator.next();
			// Busca ItemNota do Banco
			ItemNota itemNota = buscarItemByNotaItemRecibo(item);

			if (item.getQuantidade() > quantidade) {
				item.debitar(quantidade);
				itemNota.creditar(quantidade);
				quantidade = 0;

				itensAtualizados.add(item);
			} else {
				itemNota.creditar(item.getQuantidade());
				quantidade -= item.getQuantidade();
			}

		} while (quantidade > 0);

		return itensAtualizados;
	}

	private ItemNota buscarItemByNotaItemRecibo(ItemRecibo item) throws ProcessorException {
		ItemNota itemNota = null;
		try {
			ItemNotaDAO itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			itemNota = itemDAO.buscarItemByNotaItemRecibo(item);

		} catch (DAOException e) {
			throw new ProcessorException(e);
		}

		return itemNota;
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
}

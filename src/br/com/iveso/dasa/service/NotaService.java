package br.com.iveso.dasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.NotaDAO;
import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;

public class NotaService extends Service {

	private NotaDAO dao;

	public NotaService(NotaDAO dao) {
		this.dao = dao;
	}

	public void salvar(Nota nota, ProdutoService produtoService) throws ServiceException {
		try {
			creditarSaldoProdutos(nota.getItens(), produtoService);
			dao.save(nota);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void deletar(String numero) throws ServiceException {
		try {
			Nota nota = dao.load(numero);
			debitarSaldoProdutos(nota.getItens());
			dao.delete(nota);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	public List<Nota> carregarNotas() throws ServiceException {
		try {
			return dao.carregarNotas();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<ItemNota> totalPorItem(List<ItemNota> itens) {
		List<ItemNota> itensFiltrados = new ArrayList<>();

		itens.stream().map(Item::getProduto).distinct().forEach(produto -> {
			int quantidade = itens.stream().filter(item -> item.getProduto().equals(produto))
					.mapToInt(Item::getQuantidade).sum();

			itensFiltrados.add(new ItemNota(produto, quantidade));
		});

		return itensFiltrados;
	}

	public int totalItensDiferentes(List<ItemNota> itens) {
		List<Produto> produtos = itens.stream().map(Item::getProduto).distinct().collect(Collectors.toList());
		return produtos.size();
	}

	public int total(List<ItemNota> itens) {
		int quantidade = 0;
		quantidade = itens.stream().mapToInt(Item::getQuantidade).sum();
		return quantidade;
	}
	
	private void debitarSaldoProdutos(List<? extends Item> itens) throws ServiceException {
		for (Item item: itens) {
			item.getProduto().debitar(item.getQuantidade());
		}
	}

	private void creditarSaldoProdutos(List<? extends Item> itens, ProdutoService produtoService) throws ServiceException {
		for (Item item : itens) {
			Produto produtoDB = null;
			try {
				produtoDB = produtoService.buscar(item.getProduto().getCodigo());
				produtoDB.creditar(item.getQuantidade());
			} catch(NoResultException e) {
				Produto produto = item.getProduto();
				produto.creditar(item.getQuantidade());
				produtoService.salvar(produto);
			}
		}
	}

}

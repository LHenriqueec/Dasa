package com.iveso.dasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.dao.ProdutoDAO;
import com.iveso.dasa.dao.ReciboDAO;
import com.iveso.dasa.entity.Item;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.entity.Recibo;

public class ReciboService extends Service {
	private static final long serialVersionUID = 1L;

	@Inject
	private ReciboDAO dao;
	@Inject
	private NotaDAO notaDAO;
	@Inject
	private ProdutoDAO produtoDAO;

	public void salvar(Recibo recibo) throws ServiceException {
		try {
			List<Nota> notasRecibo = recalcularProdutosNota(notaDAO.getNotas(), recibo);
			recibo.setNotas(notasRecibo);
			
			beginTransaction();
			dao.salvar(recibo);
			commitTransaction();
			
			
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public void alterar(Recibo recibo) throws ServiceException {
			try {
				beginTransaction();
				dao.alterar(recibo);
				commitTransaction();
			} catch (DAOException e) {
				rollbackTransaction();
				throw new ServiceException(e);
			}
	}
	
	public void deletar(Recibo recibo) {
		//TODO: Criar método de exclusão do Recibo
	}

	public List<Recibo> listarRecibos() throws ServiceException {
		List<Recibo> recibos = null;

		try {
			recibos = dao.getRecibos();
			recibos.sort((r1, r2) -> r1.getNumero().compareTo(r2.getNumero()));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return recibos;
	}
	
	public List<Produto> completeProduto(String query) throws ServiceException {
		try {
			return produtoDAO.getProdutos().stream()
					.filter(produto -> produto.getCodigo().contains(query)
							|| produto.getNome().toUpperCase().contains(query.toUpperCase()))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Nota> completeNotas(String query) throws ServiceException {
		try {
			return notaDAO.getNotas().stream().filter(nota -> nota.getNumeroNota().contains(query))
						.collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<Nota> recalcularProdutosNota(List<Nota> notas, Recibo recibo) {
		//TODO: Refazer código utilizando Map para controlar a quantidade que sai de cada Nota
		List<Nota> notasAlteradas = new ArrayList<>();
		List<Item> itens = recibo.getItens();
		
		for (int i = 0; i < itens.size(); i++) {
			Item itemRecibo = itens.get(i);
			int qtdRecibo = itemRecibo.getQuantidade();
			int n = 0;
			
			while (qtdRecibo > 0) {
				Nota nota = notas.get(n);
				
					for (Item itemNota : nota.getItens()) {
						if (itemNota.getProduto().equals(itemRecibo.getProduto())) {
							
							if (itemNota.getQuantidade() > qtdRecibo) {
								int qtdNota = itemNota.getQuantidade();
								
								itemNota.setQuantidade(qtdNota - qtdRecibo);
								
								qtdRecibo = 0;
								
								
								if (!notasAlteradas.contains(nota)) notasAlteradas.add(nota);
								
							} else {
								qtdRecibo -= itemNota.getQuantidade();
								itemNota.setQuantidade(0);
								if (!notasAlteradas.contains(nota)) notasAlteradas.add(nota);
							}
						}
					};
					
					n++;
			}
			
			recibo.getNotas().addAll(notasAlteradas);
			
			notasAlteradas.forEach(nota -> {
				try {
					beginTransaction();
					dao.alterar(nota);
					commitTransaction();
				} catch (DAOException e) {
					rollbackTransaction();
					e.printStackTrace();
				}
			});
		};
		
		return notasAlteradas;
	}
}

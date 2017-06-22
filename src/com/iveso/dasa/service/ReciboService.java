package com.iveso.dasa.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ItemNotaDAO;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.dao.ProdutoDAO;
import com.iveso.dasa.dao.ReciboDAO;
import com.iveso.dasa.entity.ItemRecibo;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.entity.Recibo;
import com.iveso.dasa.processor.ProcessorFactory;
import com.iveso.dasa.processor.ReciboProcessor;

public class ReciboService extends Service {
	private static final long serialVersionUID = 1L;

	@Inject
	private ReciboDAO dao;
	@Inject
	private NotaDAO notaDAO;
	@Inject
	private ItemNotaDAO itemNotaDAO;
	@Inject
	private ProdutoDAO produtoDAO;
	
	public void salvar(Recibo recibo) throws ServiceException {
		try {
			beginTransaction();
			ProcessorFactory.getInstance().getReciboProcessor(recibo).salvarRecibo(dao);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

	public Recibo carregar(Recibo recibo) throws ServiceException {
		Recibo reciboDB = null;
		try {
			dao.carregar(recibo.getNumero());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return reciboDB;
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
	
	public void inserirItem(Recibo recibo, ItemRecibo item) throws ServiceException {
		try {
			new ReciboProcessor(recibo).processarItem(item, itemNotaDAO);
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

	public void deletar(Recibo recibo) throws ServiceException {
		try {
			beginTransaction();
			Recibo reciboDB = dao.carregar(recibo.getNumero(), Recibo.class);
			dao.deletar(reciboDB);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
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
}

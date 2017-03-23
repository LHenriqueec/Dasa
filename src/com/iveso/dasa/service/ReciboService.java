package com.iveso.dasa.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.dao.ReciboDAO;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Recibo;

public class ReciboService extends Service {
	private static final long serialVersionUID = 1L;

	@Inject
	private ReciboDAO dao;
	@Inject
	private NotaDAO notaDAO;

	public void salvar(Recibo recibo) throws ServiceException {
		try {
			beginTransaction();
			//TODO: Refazer o cálculo da quantidade disponível dos produtos de uma Nota.
			
			dao.salvar(recibo);
//
//			recibo.getPedidos().forEach(pedido -> {
//				try {
//					
//					Nota notaDB = notaDAO.carregar(pedido.getNota().getId(), Nota.class);
//					Produto produtoNota = notaDB.getProdutos().stream().filter(produto -> produto.equals(pedido.getProduto()))
//								.collect(Collectors.toList()).get(0);
//					
//					int quantidade = produtoNota.getQuantidade();
//					produtoNota.setQuantidade(quantidade - pedido.getProduto().getQuantidade());
//					
//					notaDAO.alterar(notaDB);
//				} catch (DAOException e) {
//					e.printStackTrace();
//				}
//			});

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
	
	public List<Nota> completeNotas(String query) throws ServiceException {
		try {
			return notaDAO.getNotas().stream().filter(nota -> nota.getNumeroNota().contains(query))
						.collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

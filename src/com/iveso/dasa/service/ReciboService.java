package com.iveso.dasa.service;

import java.util.List;

import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.dao.ReciboDAO;
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
			dao.salvar(recibo);

			recibo.getPedidos().forEach(pedido -> {
				try {
					notaDAO.alterar(pedido.getNota());
				} catch (DAOException e) {
					e.printStackTrace();
				}
			});

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
}

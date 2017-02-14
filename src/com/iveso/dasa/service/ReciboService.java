package com.iveso.dasa.service;

import java.util.ArrayList;
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
			dao.salvar(recibo);
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
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return recibos;
	}
	
	public List<Nota> completeNota(String query) throws ServiceException {
		List<Nota> notas = new ArrayList<>();
		try {
			notas.addAll(notaDAO.getNotas().stream().filter(nota -> nota.getNumeroNota().contains(query)).collect(Collectors.toList()));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return notas;
	}
}











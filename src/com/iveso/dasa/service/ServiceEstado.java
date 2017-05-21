package com.iveso.dasa.service;

import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.EstadoDAO;
import com.iveso.dasa.entity.Estado;

public class ServiceEstado extends Service {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoDAO dao;
	
	public void salvar(Estado estado) throws ServiceException {
		try {
			beginTransaction();
			dao.salvar(estado);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public void searchEstadoByNome(String uf) throws ServiceException {
		try {
			dao.searchByName(uf);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

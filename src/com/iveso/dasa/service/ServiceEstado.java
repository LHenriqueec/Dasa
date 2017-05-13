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
		salvar(dao, estado);
	}
	
	public void searchEstadoByNome(String uf) throws ServiceException {
		try {
			dao.searchByName(uf);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

package com.iveso.dasa.service;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.iveso.dasa.dao.ClienteDAO;
import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.entity.Cliente;

@ManagedBean(name="clienteService", eager=true)
@ApplicationScoped
public class ClienteService extends Service {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteDAO dao;
	
	
	public void salvar(Cliente cliente) throws ServiceException {
		try {
			beginTransaction();
			dao.salvar(cliente);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public void alterar(Cliente cliente) throws ServiceException {
		try {
			beginTransaction();
			dao.alterar(cliente);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public void deletar(Cliente cliente) throws ServiceException {
		try {
			beginTransaction();
			Cliente clienteDB = dao.carregar(cliente.getId(), Cliente.class);
			dao.deletar(clienteDB);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public Cliente getClienteById(int id) throws ServiceException {
		try {
			return dao.carregar(id, Cliente.class);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Cliente> getClientes() throws ServiceException {
		try {
			return dao.getClientes();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}

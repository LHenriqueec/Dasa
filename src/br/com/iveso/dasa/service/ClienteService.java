package br.com.iveso.dasa.service;

import java.util.List;

import br.com.iveso.dasa.dao.ClienteDAO;
import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.entity.Cliente;

public class ClienteService extends Service {
	
	ClienteDAO dao;
	
	public Cliente carregarByNome(String nome) throws ServiceException {
		try {
			ClienteDAO dao = DAOFactory.getInstance().getDAO(ClienteDAO.class);
			return dao.carregarByNome(nome);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Cliente> carregarClientes() throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ClienteDAO.class);
			return dao.carregarClientes();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void salvar(Cliente cliente) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ClienteDAO.class);
			dao.save(cliente);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}
	
	public void alterar(Cliente cliente) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ClienteDAO.class);
			dao.update(cliente);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void deletar(String cnpj) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ClienteDAO.class);
			Cliente cliente = dao.load(cnpj);
			dao.delete(cliente);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}



















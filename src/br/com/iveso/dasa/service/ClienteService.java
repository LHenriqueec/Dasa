package br.com.iveso.dasa.service;

import java.util.List;

import br.com.iveso.dasa.dao.ClienteDAO;
import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.entity.Cliente;

public class ClienteService extends Service {
	
	private ClienteDAO dao;
	
	public ClienteService(ClienteDAO dao) {
		this.dao = dao;
	}
	
	public Cliente carregarByNome(String nome) throws ServiceException {
		try {
			return dao.carregarByNome(nome);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Cliente> carregarClientesSemCompra(int index) throws ServiceException {
		try {
			int offset = 5;
			
			index *= offset;
			return dao.carregarClientesSemCompra(index, offset);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Cliente> carregarClientes() throws ServiceException {
		try {
			return dao.carregarClientes();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void salvar(Cliente cliente) throws ServiceException {
		try {
			dao.save(cliente);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}
	
	public void alterar(Cliente cliente) throws ServiceException {
		try {
			dao.update(cliente);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void deletar(String cnpj) throws ServiceException {
		try {
			Cliente cliente = dao.load(cnpj);
			dao.delete(cliente);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}



















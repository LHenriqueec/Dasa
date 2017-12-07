package br.com.iveso.dasa.service;

import br.com.iveso.dasa.dao.ClienteDAO;
import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.dao.NotaDAO;
import br.com.iveso.dasa.dao.ProdutoDAO;
import br.com.iveso.dasa.dao.ReciboDAO;

public class ServiceFactory {

	private static ServiceFactory instance;
	
	public static ServiceFactory getInstance() {
		if (instance == null) {
			instance = new ServiceFactory();
		}
		
		return instance;
	}
	
	public ReciboService getReciboService() throws ServiceException {
		try {
			return new ReciboService(DAOFactory.getInstance().getDAO(ReciboDAO.class));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public ItemNotaService getItemNotaService() throws ServiceException {
		try {
			return new ItemNotaService(DAOFactory.getInstance().getDAO(ItemNotaDAO.class));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public ClienteService getClienteService() throws ServiceException {
		try {
			return new ClienteService(DAOFactory.getInstance().getDAO(ClienteDAO.class));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}

	public NotaService getNotaService() throws ServiceException {
		try {
			return new NotaService(DAOFactory.getInstance().getDAO(NotaDAO.class));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public ProdutoService getProdutoService() throws ServiceException {
		try {
			return new ProdutoService(DAOFactory.getInstance().getDAO(ProdutoDAO.class));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
}

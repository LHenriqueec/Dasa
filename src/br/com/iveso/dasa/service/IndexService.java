package br.com.iveso.dasa.service;

import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;


public class IndexService extends Service {

	private ItemNotaDAO itemDAO;
	
	public List<ItemNota> todosItens() throws ServiceException {
		try {
			itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			return itemDAO.carregarItens();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public ItemNota carregarItemByProduto(String search) throws ServiceException {
		try {
			itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			return itemDAO.carregarItemByProduto(search);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

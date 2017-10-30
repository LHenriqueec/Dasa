package br.com.iveso.dasa.service;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;


public class ItemNotaService extends Service {

	private ItemNotaDAO itemDAO;
	
	
	public ItemNota carregarItemByProduto(String search) throws ServiceException {
		try {
			itemDAO = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
			return itemDAO.buscarItemByProduto(search);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

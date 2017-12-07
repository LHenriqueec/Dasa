package br.com.iveso.dasa.service;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;


public class ItemNotaService extends Service {

	private ItemNotaDAO dao;
	
	public ItemNotaService(ItemNotaDAO dao) {
		this.dao = dao;
	}
	
	public ItemNota carregarItemByProduto(String search) throws ServiceException {
		try {
			return dao.buscarItemByProduto(search);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

package br.com.iveso.dasa.entity.teste;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;

public class ItemNotaTeste {

	private ItemNotaDAO dao;
	
	@Before
	public void initialized() throws Exception {
		dao = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
	}
	
	@Test
	public void carregar_item_pelo_produto() throws Exception {
		System.out.println(dao.buscarItemByProduto("0010"));
	}
}

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
	public void carregar_soma_itens() throws Exception {
		dao.carregarItens().forEach(item -> System.out.println(item.getProduto() + " - " + item.getQuantidade()));
	}
	
	@Test
	public void carregar_item_pelo_produto() throws Exception {
		System.out.println(dao.carregarItemByProduto("0010"));
	}
}

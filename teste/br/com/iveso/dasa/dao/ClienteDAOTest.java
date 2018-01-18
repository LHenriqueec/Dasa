package br.com.iveso.dasa.dao;

import org.junit.Before;
import org.junit.Test;

public class ClienteDAOTest {

	private ClienteDAO dao;
	
	@Before
	public void init() throws Exception {
		dao = DAOFactory.getInstance().getDAO(ClienteDAO.class);
	}
	
	@Test
	public void busca_quantidade_clientes_sem_compra() throws Exception {
		System.out.println(dao.carregarClientesSemCompra(5, 10).size());
	}
}

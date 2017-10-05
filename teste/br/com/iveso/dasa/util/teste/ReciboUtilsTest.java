package br.com.iveso.dasa.util.teste;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.util.ReciboUtil;

public class ReciboUtilsTest {

	private ReciboDAO dao;
	ReciboUtil util;
	
	@Before
	public void init() throws Exception {
		dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
		util = new ReciboUtil();
	}
	
	@Test
	public void cria_lista_sem_produtos_repetidos() throws Exception {
		Recibo recibo = dao.load("17003");
		
		System.out.println(recibo);
	}
}

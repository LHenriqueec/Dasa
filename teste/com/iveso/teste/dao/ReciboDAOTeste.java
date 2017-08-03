package com.iveso.teste.dao;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ReciboDAO;
import com.iveso.dasa.entity.Recibo;

public class ReciboDAOTeste {
	
	private ReciboDAO dao;
	
	@Test
	public void carregar_recibo_banco() throws DAOException {
		dao = mock(ReciboDAO.class);
		
		Recibo recibo = dao.carregar("1401", Recibo.class);
		
		System.out.println(recibo);
	}
}

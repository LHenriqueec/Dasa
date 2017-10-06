package br.com.iveso.dasa.util.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.util.ReciboUtil;

public class ReciboUtilsTest {

	private ReciboDAO dao;
	
	@Before
	public void init() throws Exception {
		dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
	}
	
	@Test
	public void cria_lista_sem_produtos_repetidos() throws Exception {
		Recibo recibo = dao.load("17003");
		
		List<ItemRecibo> itens = ReciboUtil.processarItens(recibo);
		
		assertEquals(2, itens.size());
		assertEquals(450, itens.get(1).getQuantidade());
		assertEquals(550, itens.get(0).getQuantidade());
		assertEquals(1000, itens.stream().mapToInt(ItemRecibo::getQuantidade).sum());
	}
}

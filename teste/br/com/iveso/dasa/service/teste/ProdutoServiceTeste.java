package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ServiceFactory;

public class ProdutoServiceTeste {
	
	private ProdutoService service;
	
	@Before
	public void init() throws Exception {
		service = ServiceFactory.getInstance().getProdutoService();
	}
	
	@Test
	public void creditar_saldo() throws Exception {
		Produto produto = service.buscar("0012");
		
		produto.creditar(10);
		
		assertEquals(10, produto.getSaldo());
	}
}

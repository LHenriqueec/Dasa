package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;

public class ProdutoServiceTeste {
	
	private ProdutoService service;
	
	@Before
	public void init() throws Exception {
		service = ServiceFactory.getInstance().getProdutoService();
	}
	
	@Test
	public void carregar_produtos() throws ServiceException {
		List<Produto> produtos = service.carregarProdutos();
		produtos.forEach(System.out::println);
	}
	
	@Test
	public void creditar_saldo() throws ServiceException {
		Produto produto = service.buscar("0012");
		
		produto.creditar(10);
		
		assertEquals(10, produto.getSaldo());
	}
}

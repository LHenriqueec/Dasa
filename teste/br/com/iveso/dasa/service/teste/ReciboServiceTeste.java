package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ReciboService;

public class ReciboServiceTeste {

	private ReciboService service;
	private ReciboDAO dao;
	private ProdutoService produtoService;
	
	@Before
	public void init() throws Exception {
		dao = mock(ReciboDAO.class);
		service = new ReciboService(dao);
	}
	
	@Test
	public void adicionando_item_em_recibo_existente() throws Exception {
		
		//Produtos que terão o saldo atualizado
		Produto produtoDB1 = new Produto("0010", "PICOLE TESTE");
		produtoDB1.creditar(50);
		Produto produtoDB2 = new Produto("0012", "PICOLE TESTE2");
		produtoDB2.creditar(50);
		
		//Representa o recibo que será alterado
		Recibo reciboDB = new Recibo();
		reciboDB.setNumero("123");
		reciboDB.addItem(new ItemRecibo(reciboDB, produtoDB1, 20));
		
		when(dao.load("123")).thenReturn(reciboDB);
		
		//Recibo com o novo item inserido
		Recibo reciboAtualizado = new Recibo();
		reciboAtualizado.setNumero("123");
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("0010", "PICOLE TESTE"), 20));
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("0012", "PICOLE TESTE2"), 15));
		
		produtoService = mock(ProdutoService.class);
		when(produtoService.buscar("0010")).thenReturn(produtoDB1);
		when(produtoService.buscar("0012")).thenReturn(produtoDB2);
		
		service.editar(reciboAtualizado, produtoService);
		
		assertEquals(50, produtoDB1.getSaldo());
		assertEquals(35, produtoDB2.getSaldo());
		assertEquals(2, reciboDB.getItens().size());
	}
	
	@Test
	public void diminuindo_quantidade_em_um_item_do_recibo() throws Exception {
		
		//Produtos que terão o saldo atualizado
		Produto produtoDB1 = new Produto("0010", "PICOLE TESTE");
		produtoDB1.creditar(50);
		Produto produtoDB2 = new Produto("0012", "PICOLE TESTE2");
		produtoDB2.creditar(50);
		
		//Representa o recibo que será alterado
		Recibo reciboDB = new Recibo();
		reciboDB.setNumero("123");
		reciboDB.addItem(new ItemRecibo(reciboDB, produtoDB1, 20));
		reciboDB.addItem(new ItemRecibo(reciboDB, produtoDB2, 20));
		
		when(dao.load("123")).thenReturn(reciboDB);
		
		//Recibo com o saldo do produto atualizado
		Recibo reciboAtualizado = new Recibo();
		reciboAtualizado.setNumero("123");
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("0010", "PICOLE TESTE"), 10));
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("0012", "PICOLE TESTE2"), 15));
		
		//@param: produtoService pode ser null, pois não será utilizado nessa situação
		service.editar(reciboAtualizado, produtoService);
		
		assertEquals(60, produtoDB1.getSaldo());
		assertEquals(55, produtoDB2.getSaldo());
		assertEquals(2, reciboDB.getItens().size());
	}
	
	@Test
	public void aumantando_quantidade_em_um_item_do_recibo() throws Exception {
		
		//Produtos que terão o saldo atualizado
		Produto produtoDB1 = new Produto("0010", "PICOLE TESTE");
		produtoDB1.creditar(50);
		Produto produtoDB2 = new Produto("0012", "PICOLE TESTE2");
		produtoDB2.creditar(50);
		
		//Representa o recibo que será alterado
		Recibo reciboDB = new Recibo();
		reciboDB.setNumero("123");
		reciboDB.addItem(new ItemRecibo(reciboDB, produtoDB1, 20));
		reciboDB.addItem(new ItemRecibo(reciboDB, produtoDB2, 20));
		
		when(dao.load("123")).thenReturn(reciboDB);
		
		//Recibo com o saldo do produto atualizado
		Recibo reciboAtualizado = new Recibo();
		reciboAtualizado.setNumero("123");
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("0010", "PICOLE TESTE"), 30));
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("0012", "PICOLE TESTE2"), 25));
		
		//@param: produtoService pode ser null, pois não será utilizado nessa situação
		service.editar(reciboAtualizado, produtoService);
		
		assertEquals(40, produtoDB1.getSaldo());
		assertEquals(45, produtoDB2.getSaldo());
		assertEquals(2, reciboDB.getItens().size());
	}
	
	@Test
	public void alterar_recibo_removendo_um_item() throws Exception {
		
		//Representa o produto que terá o saldo devolvido
		Produto produtoDB = new Produto("0012", "PICOLE TESTE2");
		produtoDB.creditar(30);
		
		//Representa o recibo que será alterado
		Recibo reciboDB = new Recibo();
		reciboDB.setNumero("123");
		reciboDB.addItem(new ItemRecibo(reciboDB, new Produto("0010", "PICOLE TESTE"), 20));
		reciboDB.addItem(new ItemRecibo(reciboDB, produtoDB, 20));
		
		when(dao.load("123")).thenReturn(reciboDB);
		
		//Recibo teve o produto removido no front
		Recibo reciboAtualizado = new Recibo();
		reciboAtualizado.setNumero("123");
		reciboAtualizado.addItem(new ItemRecibo(reciboDB, new Produto("0010", "PICOLE TESTE"), 20));
		
		produtoService = mock(ProdutoService.class);
		service.editar(reciboAtualizado, produtoService);
		
		assertEquals(50, produtoDB.getSaldo());
		assertEquals(1, reciboDB.getItens().size());
	}
	
	@Test
	public void creditar_saldo_produto_ao_deletar_recibo() throws Exception {

		//Representa o produto no Banco de Dados
		Produto produtoDB = new Produto("0010", "PICOLE TESTE");
		produtoDB.creditar(20);
		
		Recibo recibo = new Recibo();
		recibo.setNumero("123");
		recibo.addItem(new ItemRecibo(recibo, new Produto("0010", "PICOLE TESTE"), 35));
		
		when(dao.load("123")).thenReturn(recibo);
		
		produtoService = mock(ProdutoService.class);
		when(produtoService.buscar("0010")).thenReturn(produtoDB);
		
		
		service.deletar(recibo.getNumero(), produtoService);
		verify(produtoService).buscar(produtoDB.getCodigo());
		
		assertEquals(55, produtoDB.getSaldo());
	}

	@Test
	public void debitar_saldo_produto_ao_inserir_novo_recibo() throws Exception {
		
		//Representa o produto no Banco de dados
		Produto produtoDB1 = new Produto("0010", "PICOLE TESTE");
		produtoDB1.creditar(100);
		
		Recibo recibo = new Recibo();
		recibo.addItem(new ItemRecibo(recibo, new Produto("0010", "PICOLE TESTE"), 25));
		
		produtoService = mock(ProdutoService.class);
		when(produtoService.buscar("0010")).thenReturn(produtoDB1);
		
		service.salvar(recibo, produtoService);
		verify(dao).save(recibo);
		
		assertEquals(75, produtoDB1.getSaldo());
	}
}














package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.dao.NotaDAO;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.NotaService;
import br.com.iveso.dasa.service.ProdutoService;

public class NotaServiceTeste {

	private List<ItemNota> itens;
	private NotaService service;
	private NotaDAO dao;
	
	
	@Before
	public void initialize() {
		dao = mock(NotaDAO.class);
		itens = new ArrayList<>();
		service = new NotaService(dao);
	}
	
	@Test
	public void debitar_saldo_produtos_ao_deletar_nota() throws Exception {
		
		//Representa o Produto no Banco de Dados
		Produto p1 = new Produto("0010", "PICOLE TESTE");
		p1.creditar(135);

		// Nota que será 'deletada' 
		Nota nota = new Nota("123");
		nota.getItens().add(new ItemNota(p1, 50));

		when(dao.load("123")).thenReturn(nota);
		service = new NotaService(dao);
		
		
		//Debita saldo do estoque ao deletar Nota
		service.deletar(nota.getNumero());
		
		assertEquals(85, p1.getSaldo());
	}
	
	@Test
	public void creditar_saldo_produtos_ao_salvar_nota() throws Exception {
		ProdutoService produtoServiceMock = mock(ProdutoService.class);
		
		//Produtos Mock que serão modificados ao inserir Nota
		Produto produtoDB1 = new Produto("0010", "PICOLE TESTE");
		Produto produtoDB2 = new Produto("0012", "PICOLE TESTE2");
		
		when(produtoServiceMock.buscar("0010")).thenReturn(produtoDB1);
		when(produtoServiceMock.buscar("0012")).thenReturn(produtoDB2);
		
		//Nota que será inserida no sistema
		Nota nota = new Nota("123");
		nota.getItens().add(new ItemNota(new Produto("0010", "PICOLE TESTE"), 50));
		nota.getItens().add(new ItemNota(new Produto("0012", "PICOLE TESTE2"), 75));
		
		service.salvar(nota, produtoServiceMock);
		
		//Verifica se o saldo dos produtos foi atualizado
		assertEquals(50, produtoDB1.getSaldo());
		assertEquals(75, produtoDB2.getSaldo());
	}
	
	@Test
	public void total_por_produto() {
		//Produtos inseridos através das Notas
		Produto p1 = new Produto("0010", "PICOLE LIMAO");
		int qtd1 = 50;
		
		Produto p2 = new Produto("0012", "PICOLE MORANGO");
		int qtd2 = 50;
		
		Produto p3 = new Produto("0012", "PICOLE MORANGO");
		int qtd3 = 5;
		
		Produto p4 = new Produto("0010", "PICOLE LIMAO");
		int qtd4 = 13;
		
		
		ItemNota i1 = new ItemNota(p1, qtd1);
		ItemNota i2 = new ItemNota(p2, qtd2);
		ItemNota i3 = new ItemNota(p3, qtd3);
		ItemNota i4 = new ItemNota(p4, qtd4);
		
		itens.add(i1);
		itens.add(i2);
		itens.add(i3);
		itens.add(i4);
		
		List<ItemNota> itensTotal = service.totalPorItem(itens);
		
		//Item Codigo: 0010 Nome: PICOLE LIMAO
		assertEquals(63, itensTotal.get(0).getQuantidade());
		//Item Codigo: 0012 Nome: PICOLE MORANGO
		assertEquals(55, itensTotal.get(1).getQuantidade());
		
		assertEquals(2, itensTotal.size());
	}
	
	@Test
	public void total_produtos_diferentes() {
		Produto p1 = new Produto("0010", "PICOLE LIMAO");
		int qtd1 = 50;
		
		Produto p2 = new Produto("0012", "PICOLE MORANGO");
		int qtd2 = 50;
		
		Produto p3 = new Produto("0012", "PICOLE MORANGO");
		int qtd3 = 50;
		
		Produto p4 = new Produto("0010", "PICOLE LIMAO");
		int qtd4 = 50;
		
		ItemNota i1 = new ItemNota(p1, qtd1);
		ItemNota i2 = new ItemNota(p2, qtd2);
		ItemNota i3 = new ItemNota(p3, qtd3);
		ItemNota i4 = new ItemNota(p4, qtd4);
		
		itens.add(i1);
		itens.add(i2);
		itens.add(i3);
		itens.add(i4);
		
		assertEquals(2, service.totalItensDiferentes((itens)));
		assertEquals(4, itens.size());
	}
	
	
	@Test
	public void calcular_quantidade_total_com_mais_de_um_item() {
		Produto p1 = new Produto("0010", "PICOLE LIMAO");
		int qtd1 = 50;
		
		Produto p2 = new Produto("0012", "PICOLE MORANGO");
		int qtd2 = 50;
		
		ItemNota i1 = new ItemNota(p1, qtd1);
		ItemNota i2 = new ItemNota(p2, qtd2);
		
		itens.add(i1);
		itens.add(i2);
		
		assertEquals(100, service.total(itens));
		assertEquals(2, itens.size());
	}

	@Test
	public void calcular_quantidade_total_com_um_item() {
		Produto p = new Produto("0010", "PICOLE LIMAO");
		int quantidade = 50;
		
		ItemNota item = new ItemNota(p, quantidade);
		
		itens.add(item);
		
		assertEquals(50, service.total(itens));
		assertEquals(1, itens.size());
	}
}

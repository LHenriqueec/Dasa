package br.com.iveso.dasa.entity.teste;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.NotaService;

public class NotaTeste {

	private List<Item> itens;
	private NotaService service;
	
	
	@Before
	public void initialize() {
		itens = new ArrayList<>();
		service = new NotaService();
	}
	
	@Test
	public void total_por_produto() {
		Produto p1 = new Produto("0010", "PICOLE LIMAO");
		int qtd1 = 50;
		
		Produto p2 = new Produto("0012", "PICOLE MORANGO");
		int qtd2 = 50;
		
		Produto p3 = new Produto("0012", "PICOLE MORANGO");
		int qtd3 = 5;
		
		Produto p4 = new Produto("0010", "PICOLE LIMAO");
		int qtd4 = 13;
		
		
		Item i1 = new Item(p1, qtd1);
		Item i2 = new Item(p2, qtd2);
		Item i3 = new Item(p3, qtd3);
		Item i4 = new Item(p4, qtd4);
		
		itens.add(i1);
		itens.add(i2);
		itens.add(i3);
		itens.add(i4);
		
		List<Item> itensTotal = service.totalPorItem(itens);
		
		itensTotal.forEach(System.out::println);
		
		//Item Codigo: 0010 Nome: PICOLE LIMAO
		assertEquals(63, itensTotal.get(0).getQuantidade().intValue());
		//Item Codigo: 0012 Nome: PICOLE MORANGO
		assertEquals(55, itensTotal.get(1).getQuantidade().intValue());
		
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
		
		Item i1 = new Item(p1, qtd1);
		Item i2 = new Item(p2, qtd2);
		Item i3 = new Item(p3, qtd3);
		Item i4 = new Item(p4, qtd4);
		
		itens.add(i1);
		itens.add(i2);
		itens.add(i3);
		itens.add(i4);
		
		assertEquals(2, service.totalItensDiferentes(itens));
		assertEquals(4, itens.size());
	}
	
	
	@Test
	public void calcular_quantidade_total_com_mais_de_um_item() {
		Produto p1 = new Produto("0010", "PICOLE LIMAO");
		int qtd1 = 50;
		
		Produto p2 = new Produto("0012", "PICOLE MORANGO");
		int qtd2 = 50;
		
		Item i1 = new Item(p1, qtd1);
		Item i2 = new Item(p2, qtd2);
		
		itens.add(i1);
		itens.add(i2);
		
		assertEquals(100, service.total(itens));
		assertEquals(2, itens.size());
	}

	@Test
	public void calcular_quantidade_total_com_um_item() {
		Produto p = new Produto("0010", "PICOLE LIMAO");
		int quantidade = 50;
		
		Item item = new Item(p, quantidade);
		
		itens.add(item);
		
		assertEquals(50, service.total(itens));
		assertEquals(1, itens.size());
	}
}

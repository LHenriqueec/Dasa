package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.iveso.dasa.entity.Cliente;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ClienteService;
import br.com.iveso.dasa.service.NotaService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;
import br.com.iveso.dasa.util.EstrategiaExclusaoJSON;

public class NotaServiceTeste {

	private List<ItemNota> itens;
	private NotaService service;
	
	
	@Before
	public void initialize() {
		itens = new ArrayList<>();
		service = new NotaService();
	}
	
	@Test
	public void carregar_notas_banco() throws Exception {
		List<Nota> notas = service.carregarNotas();

		Gson gson = new GsonBuilder().setExclusionStrategies(new EstrategiaExclusaoJSON()).create();
		String json = gson.toJson(notas);
		System.out.println(json);
	}
	
	@Test
	public void salvar_nota_banco() throws Exception {
		itens = new ArrayList<>();
		ItemNota i1 = new ItemNota(new Produto("0010", "PICOLE LIMAO"), 50);
		ItemNota i2 = new ItemNota(new Produto("0012", "PICOLE MORANGO"), 50);
		
		itens.add(i1);
		itens.add(i2);
		
		Cliente cliente = ServiceFactory.getInstance().getService(ClienteService.class).carregarByNome("ULTRA FRIOS");
		
		Nota nota = new Nota();
		nota.setCliente(cliente);
		nota.setData(new Date());
		nota.setNumero("123456");
		nota.setItens(itens);
		
		try {
			ConnectionUtils.beginTransaction();
			service.salvar(nota);
			i1.setNota(nota);
			i2.setNota(nota);
			ConnectionUtils.commitTransaction();
		} catch(ServiceException e) {
			ConnectionUtils.rollbackTransaction();
			e.printStackTrace();
		}
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
		
		
		ItemNota i1 = new ItemNota(p1, qtd1);
		ItemNota i2 = new ItemNota(p2, qtd2);
		ItemNota i3 = new ItemNota(p3, qtd3);
		ItemNota i4 = new ItemNota(p4, qtd4);
		
		itens.add(i1);
		itens.add(i2);
		itens.add(i3);
		itens.add(i4);
		
		List<ItemNota> itensTotal = service.totalPorItem(itens);
		
		itensTotal.forEach(System.out::println);
		
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
		
		assertEquals(2, service.totalItensDiferentes(itens));
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

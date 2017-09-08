package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.EstrategiaExclusaoJSON;

public class ReciboServiceTeste {

	private ReciboService service;
	
	@Before
	public void initialized() throws Exception {
		service = ServiceFactory.getInstance().getService(ReciboService.class);
		
	}
	
	@Test
	public void carregar_recibo() throws Exception {
		List<Recibo> recibos = service.carregarRecibos();
		Gson gson = new GsonBuilder()
				.setExclusionStrategies(new EstrategiaExclusaoJSON())
				.setDateFormat("yyyy-MM-dd")
				.create();
		
		System.out.println(gson.toJson(recibos));
	}
	
	@Test
	public void deletando_recibo_com_mais_de_uma_nota() throws Exception {
//		Ao deletar um recibo, a quantidade de cada item deve ser creditado novamente na Nota correspondente	
		Recibo recibo = new Recibo();
		recibo.setNumero("17000");
		
		// Nota que terá os valores devolvidos após a exclusão do recibo
		Nota nota1 = new Nota("123");
		List<ItemNota> itens = new ArrayList<>();
		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 10, nota1));
		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 10, nota1));
		
		Nota nota2 = new Nota("456");
		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 50, nota2));
		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 55, nota2));
		
		//Itens do Recibo com a Nota Relacionada
		List<ItemRecibo> itensRecibo = new ArrayList<>();
		itensRecibo.add(new ItemRecibo(new Produto("10", "PICOLE LIMAO"), 10, nota1));
		itensRecibo.add(new ItemRecibo(new Produto("12", "PICOLE MORANGO"), 10, nota1));
		itensRecibo.add(new ItemRecibo(new Produto("10", "PICOLE LIMAO"), 20, nota2));
		itensRecibo.add(new ItemRecibo(new Produto("12", "PICOLE MORANGO"), 15, nota2));
		recibo.setItens(itensRecibo);
		
		service.deletar(recibo, itens);
		
		assertEquals(20, itens.get(0).getQuantidade());
		assertEquals(20, itens.get(1).getQuantidade());
		assertEquals(70, itens.get(2).getQuantidade());
		assertEquals(70, itens.get(3).getQuantidade());
	}
	
	@Test
	public void deletando_recibo_com_uma_nota() throws Exception {
//		Ao deletar um recibo, a quantidade de cada item deve ser creditado novamente na Nota correspondente	
		
		Recibo recibo = new Recibo();
		recibo.setNumero("17000");
		
		// Notas que terão os valores devolvidos após a exclusão do recibo
		Nota nota1 = new Nota("123");
		List<ItemNota> itens = new ArrayList<>();
		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 10, nota1));
		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 10, nota1));
		nota1.setItens(itens);
		
		
		
		//Itens do Recibo com a Nota Relacionada
		List<ItemRecibo> itensRecibo = new ArrayList<>();
		itensRecibo.add(new ItemRecibo(new Produto("10", "PICOLE LIMAO"), 10, nota1));
		itensRecibo.add(new ItemRecibo(new Produto("12", "PICOLE MORANGO"), 10, nota1));
		recibo.setItens(itensRecibo);
		
		service.deletar(recibo, itens);
		
		assertEquals(20, nota1.getItens().get(0).getQuantidade());
		assertEquals(20, nota1.getItens().get(1).getQuantidade());
	}
	
	@Test
	public void salvar_recibo_debitando_os_valores_disponiveis_em_mais_de_uma_nota( ) throws Exception {
		
		String json = "{'data':'2017-08-29T14:50:24.640Z','numero':'17001',"
				+ "'cliente':{'cnpj':'21339044000110','nome':'ultra frios',"
				+ "'endereco':{'uf':'DF','cidade':'Brasilia','bairro':'Vicente Pires','logradouro':'shvp chacara 134 galpoies 02 e 03'},"
				+ "'contato':{'responsavel':'wellington','telefone':'6130367789','celular':'61985357721','email':'wellington@ultrafrios.com.br'}},"
				+ "'itens':[{'produto':{'codigo':'10','nome':'PICOLE LIMAO'},'quantidade':'35'},{'produto':{'codigo':'12','nome':'PICOLE MORANGO'},'quantidade':'58'}]}";
		
		Gson gson = new Gson();
		Recibo recibo = gson.fromJson(json, Recibo.class);
		
		// Itens que virão do Banco de Dados
		List<ItemNota> itens = new ArrayList<>();
		
		Nota nota1 = new Nota("123");
		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 30, nota1));
		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 55, nota1));
		
		
		Nota nota2 = new Nota("456");
		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 50, nota2));
		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 55, nota2));
		
		
		
		service.salvar(recibo, itens);
		recibo.getItens().forEach(System.out::println);
		
		assertEquals(0, itens.get(0).getQuantidade());
		assertEquals(0, itens.get(1).getQuantidade());
		assertEquals(45, itens.get(2).getQuantidade());
		assertEquals(52, itens.get(3).getQuantidade());
		assertEquals(4, recibo.getItens().size());
		
	}
	
	@Test
	public void salvar_recibo_debitando_os_valores_disponiveis_em_uma_nota( ) throws Exception {
		
		String json = "{'data':'2017-08-29T14:50:24.640Z','numero':'17001',"
				+ "'cliente':{'cnpj':'21339044000110','nome':'ultra frios',"
				+ "'endereco':{'uf':'DF','cidade':'Brasilia','bairro':'Vicente Pires','logradouro':'shvp chacara 134 galpoies 02 e 03'},"
				+ "'contato':{'responsavel':'wellington','telefone':'6130367789','celular':'61985357721','email':'wellington@ultrafrios.com.br'}},"
				+ "'itens':[{'produto':{'codigo':'10','nome':'PICOLE LIMAO'},'quantidade':'23'},{'produto':{'codigo':'12','nome':'PICOLE MORANGO'},'quantidade':'25'}]}";
		
		Gson gson = new Gson();
		Recibo recibo = gson.fromJson(json, Recibo.class);
		
		// Itens que virão do Banco de Dados
		List<ItemNota> itens = new ArrayList<>();

		Nota nota = new Nota("123");
		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 30, nota));
		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 30, nota));
		
		service.salvar(recibo, itens);
		
		assertEquals(7, itens.get(0).getQuantidade());
		assertEquals(5, itens.get(1).getQuantidade());
	}
}

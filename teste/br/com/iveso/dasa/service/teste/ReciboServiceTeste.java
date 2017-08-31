package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.service.ServiceFactory;

public class ReciboServiceTeste {

	private ReciboService service;
	
	@Before
	public void initialized() throws Exception {
		service = ServiceFactory.getInstance().getService(ReciboService.class);
	}
	
	@Test
	public void salvar_recibo_debitando_os_valores_disponiveis_em_mais_de_uma_nota( ) throws Exception {
		
		String json = "{'data':'2017-08-29T14:50:24.640Z','numero':'17001',"
				+ "'cliente':{'cnpj':'21339044000110','nome':'ultra frios',"
				+ "'endereco':{'uf':'DF','cidade':'Brasilia','bairro':'Vicente Pires','logradouro':'shvp chacara 134 galpoies 02 e 03'},"
				+ "'contato':{'responsavel':'wellington','telefone':'6130367789','celular':'61985357721','email':'wellington@ultrafrios.com.br'}},"
				+ "'itens':[{'produto':{'codigo':'10','nome':'PICOLE LIMAO'},'quantidade':'70'},{'produto':{'codigo':'12','nome':'PICOLE MORANGO'},'quantidade':'75'}]}";
		
		Gson gson = new Gson();
		Recibo recibo = gson.fromJson(json, Recibo.class);
		
		// Itens que virão do Banco de Dados
		List<ItemNota> itens = new ArrayList<>();
		
//		Nota nota1 = new Nota("123");
//		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 30, nota1));
//		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 30, nota1));
//		
//		
//		Nota nota2 = new Nota("456");
//		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 50, nota2));
//		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 50, nota2));
		
		//service.salvar(recibo, itens);
		
		assertEquals(0, itens.get(0).getQuantidade());
		assertEquals(0, itens.get(1).getQuantidade());
		assertEquals(10, itens.get(2).getQuantidade());
		assertEquals(5, itens.get(3).getQuantidade());
		
//		assertEquals(recibo.getItens().get(0).getNotas().get(0), nota1);
//		assertEquals(recibo.getItens().get(1).getNotas().get(0), nota1);
//		assertEquals(recibo.getItens().get(0).getNotas().get(1), nota2);
//		assertEquals(recibo.getItens().get(1).getNotas().get(1), nota2);
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

//		Nota nota = new Nota("123");
//		itens.add(new ItemNota(new Produto("10", "PICOLE LIMAO"), 30, nota));
//		itens.add(new ItemNota(new Produto("12", "PICOLE MORANGO"), 30, nota));
		
		//service.salvar(recibo, itens);
		
		assertEquals(7, itens.get(0).getQuantidade());
		assertEquals(5, itens.get(1).getQuantidade());
	}
}

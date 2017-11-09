package br.com.iveso.dasa.service.teste;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.processor.ItemReciboProcessorTeste;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;

public class ReciboServiceTeste {

	private ItemReciboProcessorTeste processor;
	

	@Before
	public void init() {
		processor = new ItemReciboProcessorTeste();
	}
	
	@Test
	public void edita_recibo_com_quantidade_maior_com_uma_nota_DAO() throws Exception {
		ReciboService service = ServiceFactory.getInstance().getService(ReciboService.class);
		
		try {
			// Recibo que será editado no Banco de Dados
			Recibo recibo = service.buscar("17000");
			
			
			
			// É inserido uma nova lista de itens no recibo
			recibo.setItens(Arrays.asList(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10),
					new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 70)));
			
			ConnectionUtils.closeEntityManager();
			ConnectionUtils.beginTransaction();
			service.salvar(recibo);
			ConnectionUtils.commitTransaction();
			
			assertEquals(2, recibo.getItens().size());
		
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void edita_recibo_com_quantidade_maior_com_mais_de_uma_nota() throws Exception {
		// Recibo que será editado
		Recibo recibo = new Recibo();
		recibo.setItens(new ArrayList<>());
		recibo.setNumero("17000");
		recibo.getItens().add(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10, new Nota("123")));
		recibo.getItens().add(new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 10, new Nota("123")));

		// Itens com os novos valores que serão colocados no Recibo
		List<ItemRecibo> itensNovos = Arrays.asList(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10),
													new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 70));

		// Envia o Recibo e os Novos Itens para serem processados
		processor.alterarItens(recibo, itensNovos);

		int totalRecibo = recibo.getItens().stream().mapToInt(ItemRecibo::getQuantidade).sum();

		// O Recibo é alterado com os novos valores e as Notas que foram utilizadas
		// também têm seus valores atualizados
		assertEquals(50, processor.getNota("123").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(0, processor.getNota("123").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(40, processor.getNota("456").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(80, totalRecibo);
	}

	@Test
	public void edita_recibo_com_quantidade_maior_com_uma_nota() throws Exception {
		// Recibo que será editado
		Recibo recibo = new Recibo();
		recibo.setItens(new ArrayList<>());
		recibo.setNumero("17000");
		recibo.getItens().add(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10, new Nota("123")));
		recibo.getItens().add(new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 10, new Nota("123")));

		// Itens com os novos valores que serão colocados no Recibo
		List<ItemRecibo> itensNovos = Arrays.asList(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 20),
													new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 20));

		// Envia o Recibo e os Novos Itens para serem processados
		processor.alterarItens(recibo, itensNovos);

		int totalRecibo = recibo.getItens().stream().mapToInt(ItemRecibo::getQuantidade).sum();

		// O Recibo é alterado com os novos valores e as Notas que foram utilizadas
		// também têm seus valores atualizados
		assertEquals(40, processor.getNota("123").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(40, processor.getNota("123").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(40, totalRecibo);
	}

	@Test
	public void edita_recibo_com_quantidade_igual_ao_atual() throws Exception {
		// Recibo que será editado
		Recibo recibo = new Recibo();
		recibo.setItens(new ArrayList<>());
		recibo.setNumero("17000");
		recibo.getItens().add(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10, new Nota("123")));
		recibo.getItens().add(new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 10, new Nota("123")));

		// Itens com os novos valores que serão colocados no Recibo
		List<ItemRecibo> itensNovos = Arrays.asList(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10),
				new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 10));

		// Envia o Recibo e os Novos Itens para serem processados
		processor.alterarItens(recibo, itensNovos);

		int totalRecibo = recibo.getItens().stream().mapToInt(ItemRecibo::getQuantidade).sum();

		// O Recibo é alterado com os novos valores e as Notas que foram utilizadas
		// também têm seus valores atualizados
		assertEquals(50, processor.getNota("123").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(50, processor.getNota("123").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(20, totalRecibo);
	}

	@Test
	public void editar_recibo_com_quantidade_menor_com_mais_de_uma_nota() throws Exception {
		// Recibo que será editado
		Recibo recibo = new Recibo();
		recibo.setItens(new ArrayList<>());
		recibo.setNumero("17000");
		recibo.getItens().add(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10, new Nota("123")));
		recibo.getItens().add(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 2, new Nota("456")));
		recibo.getItens().add(new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 10, new Nota("123")));
		recibo.getItens().add(new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 3, new Nota("456")));

		// Itens com os novos valores que serão colocados no Recibo
		List<ItemRecibo> itensNovos = Arrays.asList(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 5),
				new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 5));

		// Envia o Recibo e os Novos Itens para serem processados
		processor.alterarItens(recibo, itensNovos);

		int totalRecibo = recibo.getItens().stream().mapToInt(ItemRecibo::getQuantidade).sum();

		// O Recibo é alterado com os novos valores e as Notas que foram utilizadas
		// também têm seus valores atualizados
		assertEquals(55, processor.getNota("123").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(52, processor.getNota("456").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(55, processor.getNota("123").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(53, processor.getNota("456").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(10, totalRecibo);
	}

	@Test
	public void editar_recibo_com_quantidade_menor_com_uma_nota() throws Exception {
		// Recibo que será editado
		Recibo recibo = new Recibo();
		recibo.setItens(new ArrayList<>());
		recibo.setNumero("17000");
		recibo.getItens().add(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 10, new Nota("123")));
		recibo.getItens().add(new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 10, new Nota("123")));

		// Itens com os novos valores que serão colocados no Recibo
		List<ItemRecibo> itensNovos = Arrays.asList(new ItemRecibo(new Produto("0010", "PICOLE TESTE"), 5),
				new ItemRecibo(new Produto("0012", "PICOLE TESTE2"), 5));

		// Envia o Recibo e os Novos Itens para serem processados
		processor.alterarItens(recibo, itensNovos);

		int totalRecibo = recibo.getItens().stream().mapToInt(ItemRecibo::getQuantidade).sum();

		// O Recibo é alterado com os novos valores e as Notas que foram utilizadas
		// também têm seus valores atualizados
		assertEquals(55, processor.getNota("123").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(55, processor.getNota("123").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0010").getQuantidade());
		assertEquals(50, processor.getNota("456").getItemByCodigoProduto("0012").getQuantidade());
		assertEquals(10, totalRecibo);
	}
}

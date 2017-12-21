package br.com.iveso.dasa.service.teste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;
import br.com.iveso.dasa.util.Exportador;

public class ReciboServiceTeste {

	private ReciboService service;

	@Before
	public void init() throws ServiceException {
		service = ServiceFactory.getInstance().getReciboService();
	}
	
	@Test
	public void gerar_arquivo_csv() throws ServiceException {
		service.gerarArquivoCSV(new Exportador());
	}
	
	@Test
	public void carregar_recibos() throws ServiceException {
		service.carregarRecibos();
	}
	
	@Test
	public void gerar_recibo() throws ServiceException {
		service.gerarReciboPDF("1234");
	}
	
	@Test
	public void editar_recibo_removendo_item() throws Exception {
		Recibo reciboAtualizado = new Recibo();
		reciboAtualizado.setNumero("17001");
		reciboAtualizado.setItens(new ArrayList<>());
		reciboAtualizado.addItem(new ItemRecibo(reciboAtualizado, new Produto("10", "PICOLE LIMAO"), 20));
		
		try {
			ConnectionUtils.beginTransaction();
			service.editar(reciboAtualizado, null);
			ConnectionUtils.commitTransaction();
		} catch(Exception e) {
			e.printStackTrace();
			ConnectionUtils.rollbackTransaction();
		}
	}

	@Test
	public void editar_recibo() throws ServiceException {
		Recibo recibo = new Recibo();

		List<ItemRecibo> itens = Arrays.asList(new ItemRecibo(recibo, new Produto("0010", "picole limao"), 75),
				new ItemRecibo(recibo, new Produto("0012", "picole morango"), 75));

		recibo.setNumero("1234");
		recibo.setData(new Date());
		recibo.setItens(itens);

		try {
			ConnectionUtils.beginTransaction();
			service.editar(recibo, null);
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}

	@Test
	public void deletar_recibo() throws ServiceException {
		ProdutoService produtoService = ServiceFactory.getInstance().getProdutoService();
		try {
			ConnectionUtils.beginTransaction();
			service.deletar("1234", produtoService);
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}

	@Test
	public void salvar_recibo() throws ServiceException {
		Recibo recibo = new Recibo();

		List<ItemRecibo> itens = Arrays.asList(new ItemRecibo(recibo, new Produto("0010", "picole limao"), 25),
				new ItemRecibo(recibo, new Produto("0012", "picole morango"), 25));

		recibo.setNumero("1234");
		recibo.setData(new Date());
		recibo.setItens(itens);

		try {
			ConnectionUtils.beginTransaction();
			service.salvar(recibo, null);
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}
}

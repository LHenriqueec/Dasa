package br.com.iveso.dasa.service.teste;

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

public class ReciboServiceTeste {

	private ReciboService service;

	@Before
	public void init() throws ServiceException {
		service = ServiceFactory.getInstance().getReciboService();
	}
	
	@Test
	public void gerarRecibo() throws ServiceException {
		service.gerarReciboPDF("1234");
	}

	@Test
	public void editarRecibo() throws ServiceException {
		Recibo recibo = new Recibo();

		List<ItemRecibo> itens = Arrays.asList(new ItemRecibo(recibo, new Produto("0010", "picole limao"), 75),
				new ItemRecibo(recibo, new Produto("0012", "picole morango"), 75));

		recibo.setNumero("1234");
		recibo.setData(new Date());
		recibo.setItens(itens);

		try {
			ConnectionUtils.beginTransaction();
			service.editar(recibo);
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}

	@Test
	public void deletarRecibo() throws ServiceException {
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
	public void salvarRecibo() throws ServiceException {
		Recibo recibo = new Recibo();

		List<ItemRecibo> itens = Arrays.asList(new ItemRecibo(recibo, new Produto("0010", "picole limao"), 25),
				new ItemRecibo(recibo, new Produto("0012", "picole morango"), 25));

		recibo.setNumero("1234");
		recibo.setData(new Date());
		recibo.setItens(itens);

		try {
			ConnectionUtils.beginTransaction();
			service.salvar(recibo);
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}
}

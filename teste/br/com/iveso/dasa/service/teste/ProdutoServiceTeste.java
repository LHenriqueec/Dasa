package br.com.iveso.dasa.service.teste;

import org.junit.Test;

import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;

public class ProdutoServiceTeste {

	@Test
	public void salvar_produto_BD() throws Exception {
		ProdutoService service = ServiceFactory.getInstance().getService(ProdutoService.class);
		Produto produto = new Produto("0012", "picole morango");
		try {
			ConnectionUtils.beginTransaction();
			service.salvar(produto);
			ConnectionUtils.commitTransaction();
		} catch (ServiceException e) {
			ConnectionUtils.rollbackTransaction();
			e.printStackTrace();
		}
		
	}
}

package br.com.iveso.dasa.action.recibo;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ReciboService;

public class DeletarReciboAction extends Action {

	@Override
	public void process() throws Exception {

		ReciboService service = serviceFactory.getReciboService();
		ProdutoService produtoService = serviceFactory.getProdutoService();

		String numero = getRequest().getParameter("recibo");
		service.deletar(numero, produtoService);

	}

}

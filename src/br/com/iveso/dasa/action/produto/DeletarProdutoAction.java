package br.com.iveso.dasa.action.produto;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.ProdutoService;

public class DeletarProdutoAction extends Action {

	@Override
	public void process() throws Exception {
		ProdutoService service = serviceFactory.getProdutoService();
		String codigo = getRequest().getParameter("codigo");
		
		service.deletar(codigo);
		System.out.println("Produto:" + codigo + " deletado!");
	}

}

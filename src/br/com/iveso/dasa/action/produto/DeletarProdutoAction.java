package br.com.iveso.dasa.action.produto;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;

public class DeletarProdutoAction extends Action {

	@Override
	public void process() throws Exception {
		ProdutoService service = serviceFactory.getService(ProdutoService.class);
		String codigo = getRequest().getParameter("codigo");
		
		Produto produto = service.getProduto(codigo);
		
		service.deletar(produto);
		System.out.println("Produto:" + produto + " deletado!");
	}

}

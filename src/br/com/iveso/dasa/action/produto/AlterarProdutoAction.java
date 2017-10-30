package br.com.iveso.dasa.action.produto;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;

public class AlterarProdutoAction extends Action {

	@Override
	public void process() throws Exception {
		ProdutoService service = serviceFactory.getService(ProdutoService.class);
		Produto produto = new Gson().fromJson(getRequest().getParameter("produto"), Produto.class);
		service.alterar(produto);
		System.out.println("Produto:" + produto + " alterado!");
	}

}

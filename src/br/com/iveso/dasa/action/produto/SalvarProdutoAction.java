package br.com.iveso.dasa.action.produto;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;

public class SalvarProdutoAction extends Action {

	@Override
	public void process() throws Exception {
		ProdutoService service = serviceFactory.getProdutoService();
		Produto produto = new Gson().fromJson(getRequest().getParameter("produto"), Produto.class);
		service.salvar(produto);
		System.out.println("Produto:" + produto + " salvo!");
	}

}

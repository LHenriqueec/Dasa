package br.com.iveso.dasa.action.produto;

import java.util.List;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;

public class CarregarProdutosAction extends Action {

	@Override
	public void process() throws Exception {
		getResponse().setContentType("application/json");
		ProdutoService service = serviceFactory.getService(ProdutoService.class);
		
		List<Produto> produtos = service.carregarProdutos();
		String json = new Gson().toJson(produtos);
		
		getResponse().getWriter().println(json);
	}

}

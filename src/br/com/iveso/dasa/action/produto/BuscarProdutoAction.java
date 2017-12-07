package br.com.iveso.dasa.action.produto;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;

public class BuscarProdutoAction extends Action {

	@Override
	public void process() throws Exception {
		ProdutoService service = serviceFactory.getProdutoService();
		String search = getRequest().getParameter("search");
		
		Produto produto = service.buscar(search);
		
		String json = new Gson().toJson(produto);
		
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(json);
	}

}

package br.com.iveso.dasa.action.itemNota;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.service.ItemNotaService;

public class BuscarItemAction extends Action {

	@Override
	public void process() throws Exception {
		ItemNotaService service = serviceFactory.getItemNotaService();
		String search = getRequest().getParameter("search");
		ItemNota item = service.carregarItemByProduto(search);
		
		Gson gson = new Gson();
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(gson.toJson(item));
	}

}

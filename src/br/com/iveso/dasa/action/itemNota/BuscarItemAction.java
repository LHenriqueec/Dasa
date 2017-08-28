package br.com.iveso.dasa.action.itemNota;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;

public class BuscarItemAction extends Action {

	@Override
	public void process() throws Exception {
		ItemNotaDAO dao = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
		String codigo = getRequest().getParameter("search");
		ItemNota item = dao.carregarItemByProduto(codigo);
		
		Gson gson = new Gson();
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(gson.toJson(item));
	}

}

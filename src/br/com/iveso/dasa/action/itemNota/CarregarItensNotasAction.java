package br.com.iveso.dasa.action.itemNota;

import java.util.List;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;

public class CarregarItensNotasAction extends Action {

	@Override
	public void process() throws Exception {
		ItemNotaDAO dao = DAOFactory.getInstance().getDAO(ItemNotaDAO.class);
		List<ItemNota> itens = dao.carregarItens();
		
		String json = new Gson().toJson(itens);
		
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(json);
	}

}

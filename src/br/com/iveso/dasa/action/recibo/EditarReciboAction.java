package br.com.iveso.dasa.action.recibo;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ReciboService;

public class EditarReciboAction extends Action {

	@Override
	public void process() throws Exception {
		ReciboService service = serviceFactory.getReciboService();
		ProdutoService produtoService = serviceFactory.getProdutoService();

		String json = getRequest().getParameter("recibo");
		Gson gson = new Gson();
		
		Recibo recibo = gson.fromJson(json, Recibo.class);
		service.editar(recibo, produtoService);
	}

}

package br.com.iveso.dasa.action.recibo;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ReciboService;

public class SalvarReciboAction extends Action {

	@Override
	public void process() throws Exception {

		ReciboService service = serviceFactory.getReciboService();
		
		String json = getRequest().getParameter("recibo");
		Gson gson = new Gson();

		Recibo recibo = gson.fromJson(json, Recibo.class);
		recibo.getItens().forEach(item -> item.setRecibo(recibo));
		
		service.salvar(recibo);

	}

}

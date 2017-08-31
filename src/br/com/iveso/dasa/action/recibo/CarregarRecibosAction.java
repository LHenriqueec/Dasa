package br.com.iveso.dasa.action.recibo;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.util.EstrategiaExclusaoJSON;

public class CarregarRecibosAction extends Action {

	@Override
	public void process() throws Exception {
ReciboService service = serviceFactory.getService(ReciboService.class);
		
		Gson gson = new GsonBuilder().setExclusionStrategies(new EstrategiaExclusaoJSON()).create();
		
		List<Recibo> recibos = service.carregarRecibos();
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(gson.toJson(recibos));
	}

}

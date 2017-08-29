package br.com.iveso.dasa.action.cliente;

import java.util.List;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Cliente;
import br.com.iveso.dasa.service.ClienteService;
import br.com.iveso.dasa.service.ServiceFactory;

public class CarregarClientesSemCompraAction extends Action {

	@Override
	public void process() throws Exception {
		ClienteService service = ServiceFactory.getInstance().getService(ClienteService.class);
		List<Cliente> clientes = service.carregarClientesSemCompra();
		
		Gson gson = new Gson();
		
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(gson.toJson(clientes));
	}

}

package br.com.iveso.dasa.action.cliente;

import java.util.List;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Cliente;
import br.com.iveso.dasa.service.ClienteService;

public class CarregarClientesAction extends Action {


	@Override
	public void process() throws Exception {
		getResponse().setContentType("application/json");
		ClienteService service = serviceFactory.getService(ClienteService.class);
		
		List<Cliente> clientes = service.carregarClientes();
		String json = new Gson().toJson(clientes);
		
		getResponse().getWriter().println(json);
	}

}

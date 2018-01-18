package br.com.iveso.dasa.action.cliente;

import java.util.List;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Cliente;
import br.com.iveso.dasa.service.ClienteService;

public class CarregarClientesSemCompraAction extends Action {

	@Override
	public void process() throws Exception {
		ClienteService service = serviceFactory.getClienteService();
		
		int index = Integer.parseInt(getRequest().getParameter("index"));
		List<Cliente> clientes = service.carregarClientesSemCompra(index);
		
		Gson gson = new Gson();
		
		getResponse().setContentType("application/json");
		getResponse().getWriter().println(gson.toJson(clientes));
	}

}

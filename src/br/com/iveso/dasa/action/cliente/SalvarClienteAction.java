package br.com.iveso.dasa.action.cliente;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Cliente;
import br.com.iveso.dasa.service.ClienteService;

public class SalvarClienteAction extends Action {

	@Override
	public void process() throws Exception {
		ClienteService service = serviceFactory.getService(ClienteService.class);
		Cliente cliente = new Gson().fromJson(getRequest().getParameter("cliente"), Cliente.class);
		service.salvar(cliente);
		System.out.println("Cliente:" + cliente + " salvo!");
	}

}

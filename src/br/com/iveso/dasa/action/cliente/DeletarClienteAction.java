package br.com.iveso.dasa.action.cliente;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.ClienteService;

public class DeletarClienteAction extends Action {

	@Override
	public void process() throws Exception {
		ClienteService service = serviceFactory.getClienteService();
		String cnpj = getRequest().getParameter("cnpj");
		
		service.deletar(cnpj);
		System.out.println("Cliente:" + cnpj + " deletado!");
	}

}

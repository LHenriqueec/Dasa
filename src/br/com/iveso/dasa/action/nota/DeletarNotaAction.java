package br.com.iveso.dasa.action.nota;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.NotaService;

public class DeletarNotaAction extends Action {

	@Override
	public void process() throws Exception {
		NotaService service = serviceFactory.getNotaService();
		String numero = getRequest().getParameter("nota");
		
		service.deletar(numero);
		

	}

}

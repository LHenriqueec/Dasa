package br.com.iveso.dasa.action.nota;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.NotaService;
import br.com.iveso.dasa.service.ServiceFactory;

public class DeletarNotaAction extends Action {

	@Override
	public void process() throws Exception {
		NotaService service = ServiceFactory.getInstance().getService(NotaService.class);
		String numero = getRequest().getParameter("nota");
		
		service.deletar(numero);
		

	}

}

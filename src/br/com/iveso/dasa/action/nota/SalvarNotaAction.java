package br.com.iveso.dasa.action.nota;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.service.NotaService;
import br.com.iveso.dasa.service.ServiceFactory;

public class SalvarNotaAction extends Action {

	@Override
	public void process() throws Exception {
		Gson gson = new Gson();
		Nota nota = gson.fromJson(getRequest().getParameter("nota"), Nota.class);
		
		NotaService service = ServiceFactory.getInstance().getService(NotaService.class);
		service.salvar(nota);
		nota.getItens().forEach(item -> item.setNota(nota));
		
	}

}

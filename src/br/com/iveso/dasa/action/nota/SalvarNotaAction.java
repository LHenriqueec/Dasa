package br.com.iveso.dasa.action.nota;

import com.google.gson.Gson;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.service.NotaService;
import br.com.iveso.dasa.service.ProdutoService;

public class SalvarNotaAction extends Action {

	@Override
	public void process() throws Exception {
		Gson gson = new Gson();
		Nota nota = gson.fromJson(getRequest().getParameter("nota"), Nota.class);
		
		nota.getItens().forEach(Item -> Item.setNota(nota));
		
		NotaService service = serviceFactory.getNotaService();
		ProdutoService produtoService = serviceFactory.getProdutoService();
		service.salvar(nota, produtoService);
	}

}

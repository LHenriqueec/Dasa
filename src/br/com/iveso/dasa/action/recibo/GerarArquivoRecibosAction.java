package br.com.iveso.dasa.action.recibo;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.util.Exportador;

public class GerarArquivoRecibosAction extends Action {

	@Override
	public void process() throws Exception {
		ReciboService service = serviceFactory.getReciboService();
		boolean isExported = service.gerarArquivoCSV(new Exportador());
		
		getResponse().setContentType("text/json");
		getResponse().getWriter().println(isExported ? 1 : 0);
	}

}

package br.com.iveso.dasa.action.recibo;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.ReciboService;

public class ImprimirReciboAction extends Action {

	@Override
	public void process() throws Exception {
		String numero= getRequest().getParameter("numero_recibo");
		ReciboService service = serviceFactory.getService(ReciboService.class);
		
		service.gerarReciboPDF(numero);
		byte[] arquivo = Files.readAllBytes(Paths.get("/tmp/recibo.pdf"));
		
		try (ServletOutputStream out = getResponse().getOutputStream()) {
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(arquivo.length);
			out.write(arquivo, 0, arquivo.length);
			out.flush();
		}

	}

}

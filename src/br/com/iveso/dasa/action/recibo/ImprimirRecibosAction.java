package br.com.iveso.dasa.action.recibo;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;

import br.com.iveso.dasa.action.Action;
import br.com.iveso.dasa.service.ReciboService;

public class ImprimirRecibosAction extends Action {

	@Override
	public void process() throws Exception {
		ReciboService service = serviceFactory.getReciboService();
		service.gerarRecibosPDF();
		byte[] arquivo = Files.readAllBytes(Paths.get("/tmp/recibo.pdf"));
		
		try (ServletOutputStream out = getResponse().getOutputStream()) {
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(arquivo.length);
			out.write(arquivo, 0, arquivo.length);
			out.flush();
		}
	}
}

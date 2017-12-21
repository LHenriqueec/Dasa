package br.com.iveso.dasa.util.teste;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.Exportador;

public class ExportadorTest {

	private Exportador ex;
	private ReciboService service;
	
	@Before
	public void init() throws ServiceException {
		this.ex = new Exportador();
		this.service = ServiceFactory.getInstance().getReciboService();
	}
	
	@Test
	public void exportar_recibos() throws ServiceException {
		Instant instant = LocalDate.of(2017, 11, 10).atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);
		
		List<ItemRecibo> itens = service.carregarItensRecibos(data);
		ex.paraCSV(itens);
	}
}

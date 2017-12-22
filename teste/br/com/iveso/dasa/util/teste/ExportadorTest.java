package br.com.iveso.dasa.util.teste;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.entity.Cliente;
import br.com.iveso.dasa.entity.Contato;
import br.com.iveso.dasa.entity.Endereco;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.service.ClienteService;
import br.com.iveso.dasa.service.ReciboService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;
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
	
	@Test
	public void ler_arquivo_csv() throws Exception {
		Cliente cliente = null;
		ClienteService clienteService = ServiceFactory.getInstance().getClienteService();
		
		List<String> lines = Files.readAllLines(Paths.get("clientes.csv"));
		
		for (String line : lines) {
			String[] c = line.split("\\*");
			
			cliente = new Cliente();
			cliente.setNome(c[0].replaceAll("\"", ""));
			cliente.setCnpj(c[4]);

			Endereco end = new Endereco();
			end.setLogradouro(c[1]);
			end.setBairro(c[2]);
			end.setCidade(c[3]);
			cliente.setEndereco(end);
			
			Contato contato = new Contato();
			contato.setResponsavel(c[5].equals("NULL") ? "" : c[5]);
			contato.setTelefone(c[6].equals("NULL") ? "" : c[6]);
			contato.setCelular(c[7].equals("NULL") ? "" : c[7]);
			cliente.setContato(contato);
			
			try {
				ConnectionUtils.beginTransaction();
				clienteService.salvar(cliente);
				ConnectionUtils.commitTransaction();
			} catch(ServiceException e) {
				e.printStackTrace();
				ConnectionUtils.rollbackTransaction();
			}
		}
		
	}
}












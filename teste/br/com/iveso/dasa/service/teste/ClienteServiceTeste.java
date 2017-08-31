package br.com.iveso.dasa.entity.teste;


import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.service.ClienteService;
import br.com.iveso.dasa.service.ServiceFactory;

public class ClienteTeste {

	private ClienteService service;
	
	@Before
	public void initialized() throws Exception {
		service = ServiceFactory.getInstance().getService(ClienteService.class);
	}
	
	@Test
	public void carregar_clientes_sem_compra() throws Exception {
		service.carregarClientesSemCompra().forEach(System.out::println);
	}
	
}

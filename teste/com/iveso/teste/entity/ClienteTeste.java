package com.iveso.teste.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import com.iveso.dasa.entity.Cliente;

public class ClienteTeste {
	
	@Test
	public void cliente_com_mesmo_cnpj() {
		Cliente c1 = new Cliente();
		c1.setCnpj("21339044000110");
		
		Cliente c2 = new Cliente();
		c2.setCnpj("21339044000110");
		
		assertEquals(c1, c2);
	}
	
	@Test(expected=NullPointerException.class)
	public void cliente_com_cnpj_null() {
		
		Cliente c1 = new Cliente();
		
		assertNull(c1.getCnpj());
	}
}

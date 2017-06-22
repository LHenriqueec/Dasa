package com.iveso.teste.entity;

import org.junit.Before;

import com.iveso.dasa.entity.ItemRecibo;
import com.iveso.dasa.entity.Recibo;

public class ReciboTeste {

	private Recibo recibo;
	
	@Before
	public void init() {
		recibo = new Recibo();
	}
	
	public void teste() {
		recibo.getItens().add(new ItemRecibo());
	}
}

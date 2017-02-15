package com.iveso.dasa.teste;

import org.junit.BeforeClass;
import org.junit.Test;

public class Teste {

	private static long num;
	private static String var;
	
	@BeforeClass
	public static void init() {
		var = "16101";
	}
	
	@Test
	public void gerando_numero_recibo() throws Exception {
		num = Integer.parseInt(var)+1;
		
		var = String.valueOf(num);
	}
	
	@Test
	public void novo_numero_recibo() {
		System.out.println(num);
	}
}

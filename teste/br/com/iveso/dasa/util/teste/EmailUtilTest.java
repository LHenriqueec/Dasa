package br.com.iveso.dasa.util.teste;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.util.EmailUtil;

public class EmailUtilTest {
	
	private EmailUtil email;

	@Before
	public void init() {
		email = new EmailUtil();
	}
	
	@Test
	public void envia_email_test() {
		email.enviar();
	}
}

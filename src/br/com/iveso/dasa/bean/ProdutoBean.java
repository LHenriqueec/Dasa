package br.com.iveso.dasa.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int texto;

	public void salvar() {
		System.out.println("Salvando");
		texto ++;
	}
	
	public int getTexto() {
		return texto;
	}
}

package com.iveso.dasa.bean.teste;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.junit.BeforeClass;

@Named
@RequestScoped
public class BeanTeste implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int num;
	private String var;
	
	@BeforeClass
	public void init() {
		num = 5;
		var = "var";
		
		System.out.println(num);
		System.out.println(var);
	}

	
}

package com.iveso.dasa.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Estado {

	private String uf;
	
	public String getUf() {
		return uf;
	}

	
	public void setUf(String uf) {
		this.uf = uf.toUpperCase();
	}
}

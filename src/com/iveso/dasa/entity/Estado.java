package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estado {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String uf;
	
	public Integer getId() {
		return id;
	}
	
	
	public String getUf() {
		return uf;
	}

	
	public void setUf(String uf) {
		this.uf = uf.toUpperCase();
	}
}

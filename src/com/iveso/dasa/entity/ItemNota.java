package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class ItemNota extends Item {
	
	@OneToOne(fetch=FetchType.EAGER)
	private Nota nota;
	
	public ItemNota() {}
	
	public ItemNota(Nota nota, Produto produto, int quantidade) {
		this.nota = nota;
		this.produto = produto;
		this.quantidade = quantidade;
	}
	
	public Nota getNota() {
		return nota;
	}
	
	public void setNota(Nota nota) {
		this.nota = nota;
	}
}

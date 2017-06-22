package com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ItemRecibo extends Item {

	@OneToOne
	private Recibo recibo;
	@OneToOne
	private Nota nota;
	
	public ItemRecibo(){}
	
	public ItemRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
	
	public ItemRecibo(Recibo recibo, Nota nota, Produto produto, int quantidade) {
		this.recibo = recibo;
		this.nota = nota;
		this.produto = produto;
		this.quantidade = quantidade;
	}
	
	public Recibo getRecibo() {
		return recibo;
	}

	public Nota getNota() {
		return nota;
	}
	
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}
}

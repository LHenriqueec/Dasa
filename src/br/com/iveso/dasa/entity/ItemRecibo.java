package br.com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.iveso.dasa.annotaion.ExcluirJSON;

@Entity
public class ItemRecibo extends Item {

	@ExcluirJSON
	@ManyToOne
	private Recibo recibo;
	
	public ItemRecibo() {}
	
	public ItemRecibo(Recibo recibo, Produto produto, int quantidade) {
		super(produto, quantidade);
		this.recibo = recibo;
	}
	
	public Recibo getRecibo() {
		return recibo;
	}
	
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
}















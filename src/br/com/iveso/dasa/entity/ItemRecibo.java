package br.com.iveso.dasa.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.iveso.dasa.annotaion.ExcluirJSON;

@Entity
public class ItemRecibo extends Item {

	@ExcluirJSON
	@ManyToOne
	private Recibo recibo;
	
	@ManyToMany
	private List<Nota> notas;
	
	public ItemRecibo() {}
	
	public ItemRecibo(Produto produto, int quantidade) {
		super(produto, quantidade);
	}
	
	public Recibo getRecibo() {
		return recibo;
	}
	
	public List<Nota> getNotas() {
		return notas;
	}
	
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
	
	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}
}

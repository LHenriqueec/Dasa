package br.com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.iveso.dasa.annotaion.ExcluirJSON;

@Entity
public class ItemRecibo extends Item {

	@ExcluirJSON
	@ManyToOne
	private Recibo recibo;
	
	@ManyToOne
	private Nota nota;
	
	public ItemRecibo() {}
	
//	TODO: Deletar construtor, criado apenas para teste
	public ItemRecibo(Produto produto, int quantidade) {
		super(produto, quantidade);
	}
	
//	TODO: Deletar construtor, criado apenas para teste
	public ItemRecibo(Produto produto, int quantidade, Nota nota) {
		super(produto, quantidade);
		this.nota = nota;
	}
	
	public ItemRecibo(Recibo recibo, Produto produto, int quantidade, Nota nota) {
		super(produto, quantidade);
		this.recibo = recibo;
		this.nota = nota;
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
	
	@Override
	public String toString() {
		return super.toString() + "\t" + this.nota.getNumero();
	}
}

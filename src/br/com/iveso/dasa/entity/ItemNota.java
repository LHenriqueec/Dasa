package br.com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.iveso.dasa.annotaion.ExcluirJSON;

@Entity
public class ItemNota extends Item {

	@ExcluirJSON
	@ManyToOne
	private Nota nota;
	
	public ItemNota() {}
	
	public ItemNota(Produto produto, int quantidade) {
		super(produto, quantidade);
	}
	
	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public void debitar(int quantidade) {
		this.quantidade -= quantidade;
	}
}

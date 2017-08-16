package br.com.iveso.dasa.entity;

public class Produto {

	private String codigo;
	private String nome;

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
	
	@Override
	public String toString() {
		return "Codigo: " + codigo + "\t Nome: " + nome;
	}
	
}

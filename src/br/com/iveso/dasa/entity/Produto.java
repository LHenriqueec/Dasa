package br.com.iveso.dasa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Classe de Produto
 * @author Luiz Henrique
 */
@Entity
public class Produto {

	@Id
	private String codigo;
	private String nome;
	private int saldo;
	
	public Produto() {}
	
	public Produto(String codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome.toUpperCase();
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome == null ? "" : nome.toUpperCase();
	}
	
	public int getSaldo() {
		return saldo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
	
	public void creditar(int valor) {
		saldo += valor;
	}
	
	public void debitar(int valor) {
		saldo -= valor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Codigo: " + codigo + "\tNome: " + nome + "\tSaldo: " + saldo;
	}

	
}

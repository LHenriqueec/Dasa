package br.com.iveso.dasa.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Endereco {
	enum UF {
		DF, GO
	}
	
	@Enumerated(EnumType.STRING)
	private UF uf;
	private String cidade;
	private String bairro;
	private String logradouro;

	public UF getUf() {
		return uf;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
}

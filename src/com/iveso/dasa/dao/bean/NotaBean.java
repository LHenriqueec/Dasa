package com.iveso.dasa.dao.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.service.NotaService;

@Named
@SessionScoped
public class NotaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private NotaService service;
	
	private Nota nota;
	private List<Nota> notas;
	
	@PostConstruct
	private void init() {
		this.nota = new Nota();
		notas = service.todasNotas();
	}
	
	public void alterar(Nota nota) {
		
	}
	
	public void excluir(Nota nota) {
		service.excluir(nota);
		notas.remove(nota);
	}
	
	public List<Nota> getNotas() {
		return notas;
	}
	
	public Nota getNota() {
		return nota;
	}
}

package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.service.NotaService;
import com.iveso.dasa.service.ServiceException;

@Named
@RequestScoped
public class NotaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private NotaService service;

	private List<Nota> notas;

	@PostConstruct
	public void init() {
			notas = service.listar();
	}

	public void remover(Nota nota) {
			try {
				notas.remove(nota);
				service.deletar(nota);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
	}

	public List<Nota> getNotas() {
		notas.sort((n1, n2) -> n1.getNumeroNota().compareTo(n2.getNumeroNota()));
		return notas;
	}
}

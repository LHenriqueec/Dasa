package com.iveso.dasa.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.entity.Nota;

@Dependent
public class NotaService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private NotaDAO dao;
	
	public List<Nota> todasNotas() {
		List<Nota> notas = null;
		try {
			notas = dao.getNotas();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return notas;
	}
	
	@Transactional
	public boolean excluir(Nota nota) {
		boolean isDeleted = false;
		try {
			Nota notaDB = dao.carregar(nota.getNumero(), Nota.class);
			isDeleted = dao.deletar(notaDB);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}
}

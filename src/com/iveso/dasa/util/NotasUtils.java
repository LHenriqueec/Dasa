package com.iveso.dasa.util;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.entity.Nota;

public class NotasUtils {

	@Inject
	private static NotaDAO dao = new NotaDAO();
	private static List<Nota> notas;

	public static List<Nota> completeNota(String query) {
		return notas.stream().filter(nota -> nota.getNumeroNota().contains(query)).collect(Collectors.toList());
	}

	public static List<Nota> getNotas() {
		try {
			if (notas == null || notas.isEmpty()) {
				notas = dao.getNotas();
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return notas;
	}
}

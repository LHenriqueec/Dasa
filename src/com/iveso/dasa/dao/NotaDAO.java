package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Nota;

public class NotaDAO extends DAO {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Nota> getNotas() throws DAOException {
		return query("FROM Nota").getResultList();
	}

	public Nota carregarByNumeroNota(String numeroNota) throws DAOException {
		return (Nota) query("FROM Nota as n WHERE n.numeroNota :numeroNota").getResultList().get(0);
	}
}

package com.iveso.dasa.dao;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;

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

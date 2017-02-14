package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Estado;

public class EstadoDAO extends DAO {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Estado> todos() throws DAOException {
		return query("SELECT e FROM Estado e").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Estado> searchByName(String uf) throws DAOException {
		return query("SELECT e FROM Estado e WHERE uf =" + uf).getResultList();
	}
}

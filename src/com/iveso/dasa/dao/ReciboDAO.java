package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Recibo;

public class ReciboDAO extends DAO {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Recibo> getRecibos() throws DAOException {
		return query("SELECT r FROM Recibo r").getResultList();
	}
}

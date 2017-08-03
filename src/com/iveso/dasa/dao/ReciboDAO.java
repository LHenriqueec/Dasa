package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Recibo;

public class ReciboDAO extends DAO {
	private static final long serialVersionUID = 1L;

	
	public List<Recibo> getRecibos() throws DAOException {
		return query("FROM Recibo", Recibo.class).getResultList();
	}
}

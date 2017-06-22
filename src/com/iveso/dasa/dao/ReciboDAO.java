package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Recibo;

public class ReciboDAO extends DAO {
	private static final long serialVersionUID = 1L;

	public Recibo carregar(String numRecibo) throws DAOException {
		String consulta = "select r from Recibo r"
				+ " join fetch r.notas"
				+ " where r.numero = :numero";
		
		return query(consulta, Recibo.class)
				.setParameter("numero", numRecibo).getSingleResult();
	}
	
	public List<Recibo> getRecibos() throws DAOException {
		return query("FROM Recibo r", Recibo.class).getResultList();
	}
}

package com.iveso.dasa.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.iveso.dasa.entity.Recibo;

public class ReciboDAO extends DAO {
	private static final long serialVersionUID = 1L;

	public Recibo carregar(String numRecibo) throws DAOException {
		String consulta = "select r from Recibo r"
				+ " join fetch r.notas"
				+ " where r.numero = :numero";
		
		TypedQuery<Recibo> query = entity.createQuery(consulta, Recibo.class);
		query.setParameter("numero", numRecibo);
		
		return query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Recibo> getRecibos() throws DAOException {
		return query("SELECT r FROM Recibo r").getResultList();
	}
}

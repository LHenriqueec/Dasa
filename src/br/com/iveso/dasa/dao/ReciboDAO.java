package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.Recibo;

public class ReciboDAO extends DAO<Recibo> {

	public ReciboDAO() {
		super(Recibo.class);
	}
	
	public List<Recibo> carregarRecibos() throws DAOException {
		return query("from Recibo").getResultList();
	}
	
	public List<Recibo> carregarRecibosNaoGerados() throws DAOException {
		return query("from Recibo r where r.printer = false").getResultList();
	}
}

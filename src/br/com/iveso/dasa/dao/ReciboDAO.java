package br.com.iveso.dasa.dao;

import java.util.Date;
import java.util.List;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Recibo;

public class ReciboDAO extends DAO<Recibo> {

	public ReciboDAO() {
		super(Recibo.class);
	}
	
	public void removeItem(ItemRecibo item) throws DAOException {
		em.remove(item);
	}
	
	public List<Recibo> carregarRecibos() throws DAOException {
		return query("from Recibo").getResultList();
	}
	
	public List<Recibo> carregarRecibosNaoGerados() throws DAOException {
		return query("from Recibo r where r.printer = false").getResultList();
	}
	
	public List<ItemRecibo> carregarItensRecibos(Date data) throws DAOException {
		return em.createQuery("from ItemRecibo i where i.recibo.data > :data", ItemRecibo.class)
				.setParameter("data", data)
				.getResultList();
	}
}

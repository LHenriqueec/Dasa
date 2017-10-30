package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.Nota;

public class NotaDAO extends DAO<Nota> {

	public NotaDAO() {
		super(Nota.class);
	}
	
	public List<Nota> carregarNotas() throws DAOException {
		return query("from Nota").getResultList();
	}
}

package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.Cliente;

public class ClienteDAO extends DAO<Cliente> {

	protected ClienteDAO() {
		super(Cliente.class);
	}

	public List<Cliente> carregarClientes() throws DAOException {
		return query("from Cliente").getResultList();
	}

}

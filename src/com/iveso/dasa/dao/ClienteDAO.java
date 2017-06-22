package com.iveso.dasa.dao;


import java.util.List;

import com.iveso.dasa.entity.Cliente;

public class ClienteDAO extends DAO {
	private static final long serialVersionUID = 1L;

	public List<Cliente> getClientes() throws DAOException {
		return query("from Cliente", Cliente.class).getResultList();
	}
}

package com.iveso.dasa.dao;

import java.util.List;

import com.iveso.dasa.entity.Endereco;

public class EnderecoDAO extends DAO {
	private static final long serialVersionUID = 1L;

	public List<Endereco> getEnderecos() throws DAOException {
		return query("SELECT e FROM Endereco e", Endereco.class).getResultList();
	}
}

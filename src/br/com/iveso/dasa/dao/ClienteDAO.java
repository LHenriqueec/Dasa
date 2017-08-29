package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.Cliente;

public class ClienteDAO extends DAO<Cliente> {

	protected ClienteDAO() {
		super(Cliente.class);
	}

	public Cliente carregarByNome(String nome) throws DAOException {
		return query("from Cliente where nome like :nome").setParameter("nome", "%" + nome + "%").getSingleResult();
	}

	public List<Cliente> carregarClientes() throws DAOException {
		return query("from Cliente").getResultList();
	}

	public List<Cliente> carregarClientesSemCompra() throws DAOException {
		String sql = "select c from Cliente c where not exists (select numero from Recibo r where r.cliente = c or week(r.data) = week(now()))";
		return query(sql).getResultList();
	}

}

package br.com.iveso.dasa.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public Set<Cliente> carregarClientesSemCompra(int index) throws DAOException {
		Set<Cliente> clientes = new HashSet<>();
		
		clientes.addAll(query("select c from Cliente c where not exists (select r.numero from Recibo r where r.cliente = c and week(r.data) = week(now()))")
			.setMaxResults(5).setFirstResult(index).getResultList());
		
		clientes.addAll(query("select c from Cliente c where not exists (select r.numero from Recibo r where r.cliente = c)")
			.setMaxResults(5).setFirstResult(index).getResultList());
		
		return clientes;
	}

}

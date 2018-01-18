package br.com.iveso.dasa.dao;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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

	public List<Cliente> carregarClientesSemCompra(int index, int offset) throws DAOException {
		List<Cliente> clientes = null;
		
		LocalDate dt = LocalDate.now().minusDays(7);
		Instant instant = dt.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		
		String hql = "SELECT c FROM Cliente c LEFT JOIN Recibo r ON c = r.cliente "
				+ "WHERE r.data <= :data OR r.cliente IS NULL ORDER BY c.endereco.bairro";
		
		clientes = query(hql)
				.setParameter("data", Date.from(instant))
				.setFirstResult(index)
				.setMaxResults(offset)
				.getResultList();
		clientes.sort((c1, c2) -> c1.getEndereco().getBairro().compareToIgnoreCase(c2.getEndereco().getBairro()));
		
		return clientes;
	}

}

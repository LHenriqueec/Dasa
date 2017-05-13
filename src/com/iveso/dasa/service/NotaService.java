package com.iveso.dasa.service;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.iveso.dasa.dao.ClienteDAO;
import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.dao.ProdutoDAO;
import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;

@ManagedBean(name="notaService",eager=true)
@ApplicationScoped
public class NotaService extends Service {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private NotaDAO dao;
	
	@Inject
	private ProdutoDAO produtoDAO;
	
	@Inject
	private ClienteDAO clienteDAO;

	public void salvar(Nota nota) throws ServiceException {
		salvar(dao, nota);
	}

	public void deletar(Nota nota) throws ServiceException {
		try {
			beginTransaction();
			Nota notaDB = dao.carregar(nota.getNumeroNota(), Nota.class);
			dao.deletar(notaDB);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}

	public void alterar(Nota nota) throws ServiceException {
		alterar(dao, nota);
	}
	
	public Nota carregar(String id) throws ServiceException {
		try {
			return dao.carregar(id, Nota.class);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Nota carregarByNumeroNota(String numeroNota) throws ServiceException {
		try {
			return dao.carregarByNumeroNota(numeroNota);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Nota> listar() throws ServiceException {
		try {
			return dao.getNotas();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Produto> listarProdutos() throws ServiceException {
		try {
			return produtoDAO.getProdutos();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Cliente> listarClientes() throws ServiceException {
		try {
			return clienteDAO.getClientes();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

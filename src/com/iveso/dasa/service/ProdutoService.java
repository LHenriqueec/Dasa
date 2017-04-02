package com.iveso.dasa.service;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ProdutoDAO;
import com.iveso.dasa.entity.Produto;

@ManagedBean(name="produtoService", eager=true)
@ApplicationScoped
public class ProdutoService extends Service {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO dao;
	
	public void salvar(Produto produto) throws ServiceException {
		try {
			beginTransaction();
			dao.salvar(produto);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public void alterar(Produto produto) throws ServiceException {
		try {
			beginTransaction();
			dao.alterar(produto);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public void deletar(Produto produto) throws ServiceException {
		try {
			beginTransaction();
			Produto produtoDB = dao.carregar(produto.getCodigo(), Produto.class);
			dao.deletar(produtoDB);
			commitTransaction();
		} catch (DAOException e) {
			rollbackTransaction();
			throw new ServiceException(e);
		}
	}
	
	public Produto carregar(int id) throws ServiceException {
		try {
			return dao.carregar(id, Produto.class);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Produto> listarProdutos() throws ServiceException {
		try {
			return dao.getProdutos();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

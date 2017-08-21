package br.com.iveso.dasa.service;

import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.ProdutoDAO;
import br.com.iveso.dasa.entity.Produto;

public class ProdutoService extends Service {
	
	private ProdutoDAO dao;
	
	public Produto getProduto(String codigo) throws ServiceException {
		try {
			dao = daoFactory.getDAO(ProdutoDAO.class);
			return dao.load(codigo);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Produto> carregarProdutos() throws ServiceException {
		try {
			dao = daoFactory.getDAO(ProdutoDAO.class);
			return dao.carregarProdutos();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void salvar(Produto produto) throws ServiceException {
		try {
			dao = daoFactory.getDAO(ProdutoDAO.class);
			dao.save(produto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void alterar(Produto produto) throws ServiceException {
		try {
			dao = daoFactory.getDAO(ProdutoDAO.class);
			dao.update(produto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void deletar(String codigo) throws ServiceException {
		try {
			dao = daoFactory.getDAO(ProdutoDAO.class);
			Produto produto = dao.load(codigo);
			dao.delete(produto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}

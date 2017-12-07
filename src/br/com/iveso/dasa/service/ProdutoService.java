package br.com.iveso.dasa.service;

import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.ProdutoDAO;
import br.com.iveso.dasa.entity.Produto;

public class ProdutoService extends Service {
	
	private ProdutoDAO dao;
	
	public ProdutoService(ProdutoDAO dao) {
		this.dao = dao;
	}
	
	public Produto getProduto(String codigo) throws ServiceException {
		try {
			return dao.load(codigo);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Produto> carregarProdutos() throws ServiceException {
		try {
			return dao.carregarProdutos();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void salvar(Produto produto) throws ServiceException {
		try {
			dao.save(produto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void alterar(Produto produto) throws ServiceException {
		try {
			dao.update(produto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void deletar(String codigo) throws ServiceException {
		try {
			Produto produto = dao.load(codigo);
			dao.delete(produto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Produto buscar(String search) throws ServiceException {
		Produto produto = null;
		try {
			produto = dao.buscar(search);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return produto;
	}
}

package br.com.iveso.dasa.dao;

import java.util.List;

import br.com.iveso.dasa.entity.Produto;

public class ProdutoDAO extends DAO<Produto> {

	public ProdutoDAO() {
		super(Produto.class);
	}
	
	public List<Produto> carregarProdutos() throws DAOException {
		return list("from Produto");
	}

	public Produto buscar(String search) throws DAOException {
		String sql = null;
		Produto produto = null;
		try {
			int codigo = Integer.parseInt(search);
			sql = "from Produto p where p.codigo like :codigo";
			produto = query(sql).setParameter("codigo", "%" + String.valueOf(codigo) + "%").getSingleResult();
		} catch (NumberFormatException e) {
			sql = "from Produto as p where p.nome like :nome";
			produto = query(sql).setParameter("nome", "%" + search + "%").getSingleResult();
		}
		return produto;
	}

}

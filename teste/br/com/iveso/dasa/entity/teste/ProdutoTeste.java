package br.com.iveso.dasa.entity.teste;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.ProdutoDAO;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.service.ProdutoService;
import br.com.iveso.dasa.service.ServiceException;
import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;

public class ProdutoTeste {
	
	private ProdutoDAO dao;
	
	@Before
	public void started() {
		dao = new ProdutoDAO();
	}
	
	@Test
	public void buscar_produto_por_texto_digitado() throws DAOException {
		Produto produto = dao.buscar("0010");
		System.out.println(produto);
	}

	@Test
	public void carregar_todos_produtos() throws DAOException {
		dao.carregarProdutos().forEach(System.out::println);
	}
	
	@Test
	public void salvando_produto() throws DAOException {
		EntityManager em = ConnectionUtils.getEntityManager();
		try {em.getTransaction().begin();
			em.persist(new Produto("13", "Produto Teste"));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	@Test
	public void deletando_produto() throws ServiceException {
		ProdutoService service = ServiceFactory.getInstance().getProdutoService();
		try { 
			ConnectionUtils.beginTransaction();
			service.deletar("13");
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}
	
	@Test
	public void alterando_produto() throws ServiceException {
		ProdutoService service = ServiceFactory.getInstance().getProdutoService();
		Produto limao = new Produto("0010", "PICOLE LIMOES");
		Produto morango = service.getProduto("0012");
		morango.setNome("PICOLE MORANGOS");
		try {
			ConnectionUtils.beginTransaction();
			service.alterar(limao);
			service.alterar(morango);
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
		}
	}
}

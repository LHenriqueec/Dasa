package br.com.iveso.dasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.NotaDAO;
import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;

public class NotaService extends Service {
	
	public void salvar(Nota nota) throws ServiceException {
		try {
			NotaDAO dao = DAOFactory.getInstance().getDAO(NotaDAO.class);
			dao.save(nota);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Nota> carregarNotas() throws ServiceException {
		try {
			NotaDAO dao = DAOFactory.getInstance().getDAO(NotaDAO.class);
			return dao.carregarNotas();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<ItemNota> totalPorItem(List<ItemNota> itens) {
		List<ItemNota> itensFiltrados =  new ArrayList<>();
		
		itens.stream().map(Item::getProduto).distinct().forEach(produto -> {
			int quantidade = itens.stream().filter(item -> item.getProduto().equals(produto)).mapToInt(Item::getQuantidade).sum();
			
			itensFiltrados.add(new ItemNota(produto, quantidade));
		});
		
		return itensFiltrados;
	}

	public int totalItensDiferentes(List<ItemNota> itens) {
		List<Produto> produtos = itens.stream().map(Item::getProduto).distinct().collect(Collectors.toList());
		return produtos.size();
	}
	
	public int total(List<ItemNota> itens) {
		int quantidade = 0;
		quantidade = itens.stream().mapToInt(Item::getQuantidade).sum();
		return quantidade;
	}

}

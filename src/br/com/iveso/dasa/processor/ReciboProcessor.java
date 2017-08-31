package br.com.iveso.dasa.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;

public class ReciboProcessor {

	private static ReciboProcessor instance;
	private List<ItemNota> itensNota;
	
	public static ReciboProcessor getInstance() {
		if(instance == null) instance = new ReciboProcessor();
		return instance;
	}
	
	public ReciboProcessor debitarQuantidade(ItemRecibo item) {
		List<Nota> notasAfetadas = new ArrayList<>();
		int quantidade = item.getQuantidade();
		
		Iterator<ItemNota> iNota = itensNota.iterator();
		
		while(quantidade > 0 && iNota.hasNext()) {
			ItemNota itemNota = iNota.next();
			
			if(itemNota.getQuantidade() >= quantidade) {
				itemNota.debitar(quantidade);
				quantidade = 0;
			} else {
				quantidade -= itemNota.getQuantidade();
				itemNota.setQuantidade(0);
			}
			
			notasAfetadas.add(itemNota.getNota());
		}
		
		item.setNotas(notasAfetadas);
		return this;
	}
	
	public ReciboProcessor buscarItens(ItemRecibo item, ItemNotaDAO itemDAO) throws ProcessorException {
		try {
			this.itensNota = itemDAO.buscarItenByProduto(item.getProduto());
			
			return this;
		} catch (DAOException e) {
			throw new ProcessorException(e);
		}
	}
}

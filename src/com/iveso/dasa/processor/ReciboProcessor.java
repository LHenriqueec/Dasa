package com.iveso.dasa.processor;

import java.util.List;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.ItemNotaDAO;
import com.iveso.dasa.dao.ReciboDAO;
import com.iveso.dasa.entity.ItemRecibo;
import com.iveso.dasa.entity.Recibo;

public class ReciboProcessor extends Processor {
	
	private Recibo recibo;
	
	public ReciboProcessor(Recibo recibo) {
		this.recibo = recibo;
	}
	
	public void salvarRecibo(ReciboDAO reciboDAO, ItemNotaDAO itemDAO) throws DAOException {
		ProcessorFactory.getInstance().getItemProcessor().processarItens(recibo.getItens(), itemDAO, "salvar");
		reciboDAO.salvar(recibo);
	}
	
	public void deletar(ReciboDAO reciboDAO, ItemNotaDAO itemDAO) throws DAOException {
		ProcessorFactory.getInstance().getItemProcessor().processarItens(recibo.getItens(), itemDAO, "deletar");
		Recibo reciboDB = reciboDAO.carregar(recibo.getNumero(), Recibo.class);
		reciboDAO.deletar(reciboDB);
	}
	
	public void alterar(List<ItemRecibo> itens) throws DAOException {
		ProcessorFactory.getInstance().getItemProcessor().processarItensAlterados(recibo.getItens(), itens);
	}
	
	public void processarItem(ItemRecibo item, ItemNotaDAO dao) throws DAOException {
		List<ItemRecibo> itensRecibo = ProcessorFactory.getInstance().getItemProcessor().setItem(item).processorItem(dao);
		recibo.getItens().addAll(itensRecibo);
	}
}

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
	
	public void salvarRecibo(ReciboDAO reciboDAO) throws DAOException {
		reciboDAO.salvar(recibo);
	}
	
	public void processarItem(ItemRecibo item, ItemNotaDAO dao) throws DAOException {
		List<ItemRecibo> itensRecibo = ProcessorFactory.getInstance().getItemProcessor(item).processorItem(dao);
		recibo.getItens().addAll(itensRecibo);
	}
}

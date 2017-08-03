package com.iveso.dasa.processor;

import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.dao.NotaDAO;
import com.iveso.dasa.entity.ItemRecibo;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Recibo;

public class NotaProcessor extends Processor {

	public void processar(Recibo recibo, NotaDAO dao) throws DAOException {
		for (ItemRecibo item : recibo.getItens()) {
			Nota notaDB = dao.carregar(item.getNota().getNumero(), Nota.class);
			notaDB.getRecibos().add(recibo);
		}
	}
	
	public void deletarRecibo(Recibo recibo, NotaDAO dao) throws DAOException {
		for (ItemRecibo item : recibo.getItens()) {
			Nota notaDB = dao.carregar(item.getNota().getNumero(), Nota.class);
			if (notaDB.getRecibos().contains(recibo)) notaDB.getRecibos().remove(recibo);
		}
	}
}

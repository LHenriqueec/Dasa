package br.com.iveso.dasa.service;

import java.util.Iterator;
import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ItemNotaDAO;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.processor.ProcessorException;
import br.com.iveso.dasa.processor.ReciboProcessor;

public class ReciboService extends Service {

	private ReciboDAO dao;
	
	public List<Recibo> carregarRecibos() throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
			return dao.carregarRecibos();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void salvar(Recibo recibo) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
			List<ItemRecibo> itensRecibo = recibo.getItens();
			Iterator<ItemRecibo> iterator = itensRecibo.iterator();

			while (iterator.hasNext()) {

				ItemRecibo item = iterator.next();
				ReciboProcessor.getInstance().buscarItens(item, daoFactory.getDAO(ItemNotaDAO.class))
						.debitarQuantidade(item);
				item.setRecibo(recibo);
				dao.save(recibo);
			}
		} catch (ProcessorException | DAOException e) {
			throw new ServiceException(e);
		}
	}
}

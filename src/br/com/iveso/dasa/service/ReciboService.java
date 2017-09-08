package br.com.iveso.dasa.service;

import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.ItemNota;
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

	public Recibo buscar(String numero) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
			return dao.load(numero);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void salvar(Recibo recibo) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
			ReciboProcessor.getInstance().processarSalvamento(recibo);
			dao.save(recibo);
		} catch (ProcessorException | DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void deletar(String numero) throws ServiceException {
		try {
			dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
			Recibo recibo = dao.load(numero);
			ReciboProcessor.getInstance().processarExclusao(recibo);
			dao.delete(recibo);
		} catch (ProcessorException | DAOException e) {
			throw new ServiceException(e);
		}
}
	
	//TODO: Excluir método. Criado apenas para teste
	public void salvar(Recibo recibo, List<ItemNota> itens) throws ServiceException {
		try {
			ReciboProcessor.getInstance().processar(recibo, itens);
		} catch (ProcessorException e) {
			throw new ServiceException(e);
		}
	}

	public void deletar(Recibo recibo, List<ItemNota> itemNotas) throws ServiceException {
			ReciboProcessor.getInstance().processarExclusao(recibo, itemNotas);
	}
}

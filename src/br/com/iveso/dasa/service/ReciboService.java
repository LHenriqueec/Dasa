package br.com.iveso.dasa.service;

import java.util.Arrays;
import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.processor.ProcessorException;
import br.com.iveso.dasa.processor.ReciboProcessor;
import br.com.iveso.dasa.util.PdfUtil;

public class ReciboService extends Service {

	private ReciboDAO dao;
	
	public ReciboService() {
		try {
			dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public void gerarReciboPDF(String numero) throws ServiceException {
		try {
			Recibo recibo = dao.load(numero);
			gerarPDF(Arrays.asList(recibo));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void gerarRecibosPDF() throws ServiceException {
		try {
			List<Recibo> recibos = dao.carregarRecibosNaoGerados();
			gerarPDF(recibos);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

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
	
	private void gerarPDF(List<Recibo> recibos) {
		PdfUtil pdf = new PdfUtil();
		pdf.gerarPdf(recibos);
		recibos.forEach(recibo -> recibo.setPrinter(true));
	}
}

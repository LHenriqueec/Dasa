package br.com.iveso.dasa.service;

import java.util.Arrays;
import java.util.List;

import br.com.iveso.dasa.dao.DAOException;
import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.util.PdfUtil;

public class ReciboService extends Service {

	private ReciboDAO dao;

	public ReciboService(ReciboDAO dao) {
		this.dao = dao;
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

	/**
	 * Atualiza os Itens do Recibo
	 * 
	 * @param reciboAtualizado
	 *            Recibo com itens atualizados
	 * @throws ServiceException
	 */
	public void editar(Recibo reciboAtualizado) throws ServiceException {
		try {
			Recibo reciboDB = dao.load(reciboAtualizado.getNumero());
			
			for(Item item : reciboDB.getItens()) {
				ItemRecibo itemAtualizado = reciboAtualizado.getItens().stream().filter(itemNew -> itemNew.getProduto().equals(item.getProduto())).findFirst().get();
				int diferenca = 0;
				
				if(itemAtualizado.getQuantidade() > item.getQuantidade()) {
					diferenca = itemAtualizado.getQuantidade() - item.getQuantidade();
					item.creditar(diferenca);
				} else {
					diferenca = item.getQuantidade() - itemAtualizado.getQuantidade();
					item.debitar(diferenca);
				}
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Recibo> carregarRecibos() throws ServiceException {
		try {
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
			debitarSaldoProduto(recibo.getItens());
			dao.save(recibo);
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void deletar(String numero, ProdutoService produtoService) throws ServiceException {
		try {
			Recibo reciboDB = dao.load(numero);
			creditarSaldoProduto(reciboDB.getItens(), produtoService);
			dao.delete(reciboDB);
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private void debitarSaldoProduto(List<? extends Item> itens) throws ServiceException {
		for(Item item : itens) {
			item.getProduto().debitar(item.getQuantidade());
		}
	}
	
	private void creditarSaldoProduto(List<? extends Item> itens, ProdutoService produtoService) throws ServiceException {
		for(Item item : itens) {
			Produto produtoDB = produtoService.buscar(item.getProduto().getCodigo());
			produtoDB.creditar(item.getQuantidade());
		}
	}

	private void gerarPDF(List<Recibo> recibos) {
		PdfUtil pdf = new PdfUtil();
		pdf.gerarPdf(recibos);
		recibos.forEach(recibo -> recibo.setPrinter(true));
	}
}

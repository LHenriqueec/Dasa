package br.com.iveso.dasa.service;

import java.util.Arrays;
import java.util.Iterator;
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
	public void editar(Recibo reciboAtualizado, ProdutoService produtoService) throws ServiceException {
		Recibo reciboDB = null;
		
		try {
			reciboDB = dao.load(reciboAtualizado.getNumero());
			
			// Verifica se algum item foi excluído do Recibo
			if(reciboAtualizado.getItens().size() < reciboDB.getItens().size()) {
				for(Iterator<ItemRecibo> i = reciboDB.getItens().iterator(); i.hasNext();) {
					ItemRecibo item = i.next();
					if(!reciboAtualizado.contem(item)) {
						reciboDB.removeItem(item);
						dao.removeItem(item);
						i.remove();
					}
				}
			}
			
			for(ItemRecibo itemAtualizado : reciboAtualizado.getItens()) {
				if(reciboDB.getItens().contains(itemAtualizado)) {
					Item itemRecibo = reciboDB.getItens().stream().filter(itemDB -> itemDB.equals(itemAtualizado)).findFirst().get();
					atualizarItem(itemRecibo, itemAtualizado);
				} else {
					reciboDB.addItem(itemAtualizado);
					debitarSaldoProduto(Arrays.asList(itemAtualizado), produtoService);
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

	public void salvar(Recibo recibo, ProdutoService produtoService) throws ServiceException {
		try {
			debitarSaldoProduto(recibo.getItens(), produtoService);
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
	
	private void atualizarItem(Item itemRecibo, Item updateItem) {
		int diferenca = 0;
		
		if(updateItem.getQuantidade() > itemRecibo.getQuantidade()) {
			diferenca = updateItem.getQuantidade() - itemRecibo.getQuantidade();
			itemRecibo.creditar(diferenca);
		} else {
			diferenca = itemRecibo.getQuantidade() - updateItem.getQuantidade();
			itemRecibo.debitar(diferenca);
		}
	}
	
	private void debitarSaldoProduto(List<? extends Item> itens, ProdutoService produtoService) throws ServiceException {
		for(Item item : itens) {
			Produto produtoDB = produtoService.buscar(item.getProduto().getCodigo());
			produtoDB.debitar(item.getQuantidade());
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

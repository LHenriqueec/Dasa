package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Pedido;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.entity.ProdutoPedido;
import com.iveso.dasa.entity.Recibo;
import com.iveso.dasa.service.ReciboService;
import com.iveso.dasa.service.ServiceException;

@Named
@SessionScoped
public class ReciboBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ReciboService service;

	private Recibo recibo;
	private List<Recibo> recibos;
	private Pedido pedido;

	@PostConstruct
	private void init() {
		try {
			pedido = new Pedido();
			recibos = service.listarRecibos();
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public String novoRecibo() {
		recibo = new Recibo();
		gerarNumeroRecibo(recibo);
		return "lista_clientes_recibo";
	}

	public String salvar() {
		try {
			recibos.add(recibo);
			service.salvar(recibo);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "recibos";
	}

	public String selecionar(Cliente cliente) {
		recibo.setCliente(cliente);
		
		return "novo_recibo";
	}
	
	public void mostrarDetalhe(Recibo recibo) {
		this.recibo = recibo;
		openDialog("detalhes_recibo");
	}

	public List<Nota> completeNota(String query) {
		List<Nota> filter = null;
		try {
			filter = service.completeNota(query);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return filter;
	}

	public List<Produto> completeProduto(String query) {
		List<Produto> filter = null;
		filter = pedido.getNota().getProdutosPedidos().stream().map(ProdutoPedido::getProduto)
				.filter(produto -> produto.getCodigo().contains(query)
						|| produto.getNome().toUpperCase().contains(query.toUpperCase()))
				.collect(Collectors.toList());

		return filter;
	}

	public void inserirPedido() {
		recibo.getPedidos().add(pedido);
		
		pedido = new Pedido();
	}

	public int totalProdutos() {
		return recibo.getPedidos().stream().map(Pedido::getPedidoProduto).mapToInt(ProdutoPedido::getQuantidade).sum();
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}

	public Recibo getRecibo() {
		return recibo;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	private void gerarNumeroRecibo(Recibo recibo) {
		long ultimo = recibos.stream().mapToLong(rec -> Long.parseLong(rec.getNumero())).max().getAsLong()+1;
		recibo.setNumero(String.valueOf(ultimo));
	}
	
	private void openDialog(String dialog) {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("resizable", false);
		
		RequestContext.getCurrentInstance().openDialog(dialog, options, null);
	}
}

package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.entity.Item;
import com.iveso.dasa.entity.ItemRecibo;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;
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
	private ItemRecibo item;
	private boolean edit;

	@PostConstruct
	private void init() {
		try {
			recibos = service.listarRecibos();
			item = new ItemRecibo(recibo);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public String novo() {
		edit = false;
		recibo = new Recibo();
		gerarNumeroRecibo(recibo);
		return "lista_clientes_recibo";
	}

	public String alterar(Recibo recibo) {
		edit = true;
		this.recibo = recibo;
		return "lista_clientes_recibo";
	}

	public String salvar() {

		try {
			if (edit) {
				service.alterar(recibo);
			} else {
				recibos.add(recibo);
				service.salvar(recibo);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "recibos";
	}

	public void excluir(Recibo recibo) {
		try {
			recibos.remove(recibo);
			service.deletar(recibo);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public String selecionar(Cliente cliente) {
		recibo.setCliente(cliente);

		return "novo_recibo";
	}

	public void mostrarDetalhe(Recibo recibo) {
		this.recibo = recibo;
		openDialog("detalhes_recibo");
	}

	public String imprimir(Recibo recibo) {
		try {
			this.recibo = service.carregar(recibo);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "recibo_dialog";
	}

	public String notasRecibo() {
		StringBuffer buff = new StringBuffer();
		
		int i = 0;
		do {
			
			buff.append(recibo.getItens().get(i).getNota().getNumeroNota());
			if (i < recibo.getItens().size()) buff.append(";");
			i++;
		} while(i < recibo.getItens().size());
		
		return buff.toString();
	}

	public List<Nota> completeNota(String query) {
		List<Nota> complete = null;
		try {
			complete = service.completeNotas(query);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return complete;
	}

	public List<Produto> completeProduto(String query) {
		List<Produto> complete = null;
		try {
			complete = service.completeProduto(query);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return complete;
	}

	public void inserirItem() {
		try {
			//TODO: Corrigir a maneira que os produtos são exibidos na hora da inserção. *Produtos aparecendo duplicados
			service.inserirItem(recibo, item);
			item = new ItemRecibo(recibo);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public int totalProdutos() {
		return recibo.getItens().stream().mapToInt(item -> item.getQuantidade()).sum();
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}

	public Recibo getRecibo() {
		return recibo;
	}

	public Item getItem() {
		return item;
	}

	private void gerarNumeroRecibo(Recibo recibo) {
		if (recibos.size() <= 0) {
			recibo.setNumero("1426");
		} else {
			long ultimo = recibos.stream().mapToLong(rec -> Long.parseLong(rec.getNumero())).max().getAsLong() + 1;
			recibo.setNumero(String.valueOf(ultimo));
		}
	}

	private void openDialog(String dialog) {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("resizable", false);

		RequestContext.getCurrentInstance().openDialog(dialog, options, null);
	}
}

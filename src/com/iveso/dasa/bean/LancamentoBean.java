package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.entity.Item;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.service.NotaService;
import com.iveso.dasa.service.ServiceException;

@Named
@SessionScoped
public class LancamentoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private NotaService service;

	private Nota nota;
	private boolean edit;
	private Item item;
	
	
	public String novo() {
		edit = false;
		nota = new Nota();
		item = new Item();
		return "lancamento_produto";
	}
	
	public String alterar(Nota nota) {
		edit = true;
		setNota(nota);
		return "lancamento";
	}
	
	public String salvar() {
		try {
			if (edit) {
				service.alterar(nota);
			} else {
				service.salvar(nota);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	public String gerar() {
		return "lancamento";
	}
	
	public void selecionar(Cliente cliente) {
		nota.setCliente(cliente);
	}
	
	public void inserirItem() {
		//TODO: Refazer a inserção dos Items no Sistema
		nota.getItens().add(item);
		item = new Item();
	}
	
	public List<Cliente> listarClientes() {
		try {
			return service.listarClientes();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Produto> complete(String query) {
		List<Produto> produtos = null;
		try {
			produtos = service.listarProdutos().stream().filter(produto -> produto.getCodigo().contains(query)
					|| produto.getNome().toUpperCase().contains(query.toUpperCase())).collect(Collectors.toList());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return produtos;
	}
	
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
	public Nota getNota() {
		return nota;
	}
	
	public Item getItem() {
		return item;
	}
}

package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.entity.ProdutoPedido;
import com.iveso.dasa.service.NotaService;
import com.iveso.dasa.service.ServiceException;

@Named
@SessionScoped
public class LancamentoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private NotaService service;

	private Nota nota;
	private ProdutoPedido produtoPedido;
	private boolean edit;
	
	@PostConstruct
	private void init() {
		nota = new Nota();
		produtoPedido = new ProdutoPedido();
	}
	
	public String novo() {
		edit = false;
		init();
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
	
	public void inserirProduto() {
		nota.getProdutosPedidos().add(produtoPedido);
		produtoPedido = new ProdutoPedido();
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
	
	public ProdutoPedido getProdutoPedido() {
		return produtoPedido;
	}
}

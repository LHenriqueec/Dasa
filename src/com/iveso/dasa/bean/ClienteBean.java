package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.entity.Contato;
import com.iveso.dasa.entity.Endereco;
import com.iveso.dasa.entity.Estado;
import com.iveso.dasa.service.ClienteService;
import com.iveso.dasa.service.ServiceException;
import com.iveso.dasa.util.UFUtils;

@Named
@SessionScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService service;

	private List<Cliente> clientes;
	private List<Cliente> filterClientes;
	private Cliente cliente;
	private boolean edit;
	private int index;

	public ClienteBean() {
		this.clientes = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		try {
			clientes.addAll(service.getClientes());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void novo_cliente() {
		cliente = new Cliente(new Endereco(new Estado()), new Contato());
		openDialog();
	}

	public void salvar() {
		try {

			if (edit) {
				service.alterar(cliente);
				clientes.set(index, cliente);
				edit = false;
				cliente = null;
			} else {
				clientes.add(cliente);
				service.salvar(cliente);
				cliente = null;
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void salvar_exit() {
		RequestContext.getCurrentInstance().closeDialog(cliente);
	}

	public void editar(Cliente cliente) {
		this.cliente = cliente;
		index = clientes.indexOf(cliente);
		edit = true;
		openDialog();
	}

	public void deletar(Cliente cliente) {
		try {
			clientes.remove(cliente);
			service.deletar(cliente);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> allUF() {
		return UFUtils.getUfs();
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public boolean isEdit() {
		return edit;
	}
	
	public List<Cliente> getFilterClientes() {
		return filterClientes;
	}
	
	public void setFilterClientes(List<Cliente> filterClientes) {
		this.filterClientes = filterClientes;
	}
	
	private void openDialog() {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("resizable", false);
		options.put("contentWidth", "680");
		options.put("contentHeight", "480");
		

		RequestContext.getCurrentInstance().openDialog("novo_cliente", options, null);
	}
}

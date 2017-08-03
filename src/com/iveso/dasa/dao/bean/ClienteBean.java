package com.iveso.dasa.dao.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.service.ClienteService;

@Named
@SessionScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService service;

	private boolean edit = false;
	private List<Cliente> clientes;
	private Cliente cliente;

	@PostConstruct
	private void init() {
		cliente = new Cliente();
		clientes = service.allClientes();
	}

	public void salvar() {
		if (edit) {
			service.alterar(cliente);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente alterado com sucesso!"));
		} else {
			service.salvar(cliente);
			clientes.add(cliente);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente salvo com sucesso!"));
		}

		cliente = new Cliente();
		edit = false;
	}

	public void alterar(Cliente cliente) {
		this.cliente = cliente;
		edit = true;
	}

	public void deletar(Cliente cliente) {
		if (service.deletar(cliente)) {
			clientes.remove(cliente);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente excluído com sucesso!"));
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro na aplicação!", "Verifique o SQL"));
		}

		this.cliente = new Cliente();
		this.edit = false;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}
}

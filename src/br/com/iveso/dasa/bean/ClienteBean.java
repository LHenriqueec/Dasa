package br.com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.iveso.dasa.entity.Cliente;

@Named
@RequestScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Cliente> clientes;
	private Cliente cliente;
	
	@PostConstruct
	private void started() {
		clientes = new ArrayList<>();
		cliente = new Cliente();
	}
	
	public void novo() {
		
	}
	
	public void salvar() {
		
	}
	
	public void alterar(Cliente cliente) {
		
	}
	
	public void deletar(Cliente cliente) {
		
	}
	
	public void todosUFs() {
		
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
}

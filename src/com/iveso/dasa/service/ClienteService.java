package com.iveso.dasa.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.iveso.dasa.dao.ClienteDAO;
import com.iveso.dasa.dao.DAOException;
import com.iveso.dasa.entity.Cliente;

public class ClienteService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteDAO dao;
	
	@Transactional
	public List<Cliente> allClientes() {
		List<Cliente> clientes = null;
		try {
			clientes = dao.getClientes();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return clientes;
	}
	
	@Transactional
	public void salvar(Cliente cliente) {
		try {
			dao.salvar(cliente);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void alterar(Cliente cliente) {
		try {
			dao.alterar(cliente);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public boolean deletar(Cliente cliente) {
		boolean isDeleted = false;
		try {
			Cliente clienteDB = dao.carregar(cliente.getCnpj(), Cliente.class);
			isDeleted = dao.deletar(clienteDB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(isDeleted);
		return isDeleted;
	}
}

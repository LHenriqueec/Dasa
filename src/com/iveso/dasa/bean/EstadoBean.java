package com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.iveso.dasa.entity.Estado;
import com.iveso.dasa.service.ServiceEstado;
import com.iveso.dasa.service.ServiceException;

@Named
@RequestScoped
public class EstadoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ServiceEstado service;
	private Estado estado;
	
	public void novo() {
		estado = new Estado();
		openDialog();
	}
	
	public void salvar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void searchByUF(String uf) {
		try {
			service.searchEstadoByNome(uf);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public Estado getEstado() {
		return estado;
	}
	
	private void openDialog() {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("resizable", false);
		RequestContext.getCurrentInstance().openDialog("novo_estado", options, null);
	}
}

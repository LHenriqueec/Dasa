package br.com.iveso.dasa.service;

import br.com.iveso.dasa.dao.DAOFactory;

public class Service {

	protected ServiceFactory serviceFactory;
	protected DAOFactory daoFactory;
	
	protected Service() {
		this.serviceFactory = ServiceFactory.getInstance();
		this.daoFactory = DAOFactory.getInstance();
	}
}

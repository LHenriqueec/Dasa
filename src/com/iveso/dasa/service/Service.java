package com.iveso.dasa.service;

import com.iveso.dasa.dao.DAO;
import com.iveso.dasa.dao.DAOException;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

@RequestScoped
public abstract class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Resource
	private UserTransaction ut;
	
	protected void commitTransaction() {
		try {
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void beginTransaction() {
		try {
			ut.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void rollbackTransaction() {
		try {
			if(ut.getStatus() == Status.STATUS_ACTIVE)
			ut.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	protected void salvar(DAO dao, Object obj) throws ServiceException {
//		try {
//			beginTransaction();
//			dao.salvar(obj);
//			commitTransaction();
//		} catch (DAOException e) {
//			rollbackTransaction();
//			throw new ServiceException(e);
//		}
//	}
//
//	protected void alterar(DAO dao, Object obj) throws ServiceException {
//		try {
//			beginTransaction();
//			dao.alterar(obj);
//			commitTransaction();
//		} catch (DAOException e) {
//			rollbackTransaction();
//			throw new ServiceException(e);
//		}
//	}
}

package com.iveso.dasa.dao;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@RequestScoped
public abstract class DAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "Dasa")
	protected EntityManager entity;

	public <T> T carregar(Object key, Class<T> clazz) throws DAOException {
		return entity.find(clazz, key);
	}
	
	public <T> void salvar(T obj) throws DAOException {
		try {
			entity.persist(obj);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public <T> void deletar(T obj) throws DAOException {
		try {
			entity.remove(obj);
			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public <T> T alterar(T obj) throws DAOException {
		T t = null;
		try {
			t = entity.merge(obj);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		
		return t;
	}

	protected <T> TypedQuery<T> query(String jpql, Class<T> clazz) throws DAOException {
		return entity.createQuery(jpql, clazz);
	}
}
package br.com.iveso.dasa.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;


import br.com.iveso.dasa.util.ConnectionUtils;

public class DAO<T> {

	private EntityManager em;
	private Class<T> clazz;
	
	protected DAO(Class<T> clazz) {
		em = ConnectionUtils.getEntityManager();
		this.clazz = clazz;
	}
	
	public T load(Serializable id) throws DAOException {
		try {
			return (T) em.find(clazz, id);
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}
	
	public void save(T obj) throws DAOException {
			em.persist(obj);
	}
	
	public void delete(T obj) throws DAOException {
			em.remove(obj);
	}
	
	public void update(T obj) throws DAOException {
			em.merge(obj);
	}
	
	public List<T> list(String hql) throws DAOException {
		try {
			TypedQuery<T> q = query(hql);
			return q.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}
	
	public T result(String hql, Class<T> clazz) throws DAOException {
		try {
			return query(hql).getSingleResult();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}
	
	protected TypedQuery<T> query(String hql) throws DAOException {
		try {
			return em.createQuery(hql, clazz);
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}
}














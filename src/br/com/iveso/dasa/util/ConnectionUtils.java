package br.com.iveso.dasa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ConnectionUtils {
//TODO: A página tem que aguardar até a o commit esteja completo!
	
	private static EntityManagerFactory factory;
	private static ThreadLocal<EntityManager> threadEntityManager;
	
	static {
		if(factory == null)
		factory = Persistence.createEntityManagerFactory("Dasa");
		threadEntityManager = new ThreadLocal<>();
	}
	
	public static EntityManager getEntityManager() {
		if(threadEntityManager.get() == null || !threadEntityManager.get().isOpen()) {
			threadEntityManager.set(factory.createEntityManager());
		}
		return threadEntityManager.get();
	}
	
	public static void beginTransaction() {
		getEntityManager();
		threadEntityManager.get().getTransaction().begin();
	}
	
	public static void commitTransaction() {
		threadEntityManager.get().getTransaction().commit();
	}
	
	public static void rollbackTransaction() {
		threadEntityManager.get().getTransaction().rollback();
	}
	
	public static void closeEntityManager() {
		EntityManager em = threadEntityManager.get();
		if (em != null) {
			EntityTransaction transaction = em.getTransaction();
			
			if (transaction.isActive()) {
				transaction.commit();
			}
			
			em.close();
			threadEntityManager.set(null);;
		}
	}
}

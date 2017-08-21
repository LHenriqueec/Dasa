package br.com.iveso.dasa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionUtils {

	
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
}

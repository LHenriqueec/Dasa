package br.com.iveso.dasa.dao;

import java.lang.reflect.InvocationTargetException;

public class DAOFactory {

	private static DAOFactory instance;
	
	public static DAOFactory getInstance() {
		if(instance == null) {
			instance = new DAOFactory();
		}
		
		return instance;
	}
	
	public <T extends DAO<?>> T getDAO(Class<T> clazz) throws DAOException {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new DAOException(e);
		}
			
	}
}

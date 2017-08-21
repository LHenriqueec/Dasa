package br.com.iveso.dasa.dao;

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
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new DAOException(e);
		} catch (IllegalAccessException e) {
			throw new DAOException(e);
		}
	}
}

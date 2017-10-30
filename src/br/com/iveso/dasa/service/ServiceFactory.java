package br.com.iveso.dasa.service;

import java.lang.reflect.InvocationTargetException;

public class ServiceFactory {

	private static ServiceFactory instance;
	
	public static ServiceFactory getInstance() {
		if (instance == null) {
			instance = new ServiceFactory();
		}
		
		return instance;
	}
	
	public <T extends Service> T getService(Class<T> clazz) throws ServiceException {
		try {
			T service = clazz.getDeclaredConstructor().newInstance();
			return service;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new ServiceException(e);
		} 
	}
}

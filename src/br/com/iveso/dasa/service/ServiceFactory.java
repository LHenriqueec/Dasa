package br.com.iveso.dasa.service;

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
			T service = clazz.newInstance();
			return service;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException 
				| SecurityException e) {
			throw new ServiceException(e);
		} 
	}
}

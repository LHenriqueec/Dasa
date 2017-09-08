package br.com.iveso.dasa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.iveso.dasa.service.ServiceFactory;
import br.com.iveso.dasa.util.ConnectionUtils;

public abstract class Action {

	private HttpServletRequest request;
	private HttpServletResponse response;
	protected ServiceFactory serviceFactory;
	
	protected Action() {
		this.serviceFactory = ServiceFactory.getInstance();
	}

	public void runAction() throws Exception {
		try {
			ConnectionUtils.closeEntityManager();
			ConnectionUtils.beginTransaction();
			process();
			ConnectionUtils.commitTransaction();
		} catch (Exception e) {
			ConnectionUtils.rollbackTransaction();
			throw e;
		}
	}

	public abstract void process() throws Exception;

	public HttpSession getSession() {
		return request.getSession();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
